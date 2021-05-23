package com.baoge;

import com.baoge.entity.User;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author shaoxubao
 * @Date 2019/9/26 10:47
 */
public class TestMyBatisExecutor {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&autoR&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";
    private final String JDBC_USER_NAME = "root";
    private final String JDBC_PASSWORD = "12345678";

    private Configuration configuration;

    private Connection connection;

    private JdbcTransaction jdbcTransaction;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(TestMyBatisExecutor.class.getResourceAsStream("/Configuration.xml"));
        configuration = factory.getConfiguration();
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD);
        jdbcTransaction = new JdbcTransaction(connection);
    }

    /**
     * 简单执行器
     */
    @Test
    public void testSimpleExecutor() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement statement = configuration.getMappedStatement("com.baoge.mapper.UserMapper.getUser");
        List<User> userList = executor.doQuery(statement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                statement.getBoundSql(1));
        System.out.println(userList.get(0));
    }

    /**
     * 复用执行器:只会预编译一次
     */
    @Test
    public void testReuseExecutor() throws SQLException {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement statement = configuration.getMappedStatement("com.baoge.mapper.UserMapper.getUser");
        List<User> userList = executor.doQuery(statement, 1, RowBounds.DEFAULT, ReuseExecutor.NO_RESULT_HANDLER,
                statement.getBoundSql(1));
        executor.doQuery(statement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                statement.getBoundSql(1));
        System.out.println(userList.get(0));
    }

    /**
     * 批处理执行器
     * 针对增删改操作，批处理操作必须是手动刷新
     */
    @Test
    public void testBatchExecutor() throws SQLException {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement statement = configuration.getMappedStatement("com.baoge.mapper.UserMapper.updateUser");

        for (int i = 1; i <= 10; i++) {
            executor.doUpdate(statement, new User(i, "baoge+" + i));
        }

        executor.doFlushStatements(false);
    }

    /**
     * 基础处理器
     * 若localCacheScope设置为STATEMENT，则会刷新一级缓存
     */
    @Test
    public void testBaseExecutor() throws SQLException {
        Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement statement = configuration.getMappedStatement("com.baoge.mapper.UserMapper.getUser");
        executor.query(statement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        executor.query(statement, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    }


}
