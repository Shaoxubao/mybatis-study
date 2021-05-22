package com.baoge;

import com.baoge.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * @Author shaoxubao
 * @Date 2019/9/25 21:00
 */
public class MybatisHelloWorld {

    public static void main(String[] args) {
        String resource = "Configuration.xml";
        Reader reader;
        try {
            // 1.加载mybatis的配置文件，初始化mybatis，创建出SqlSessionFactory，是创建SqlSession的工厂
            // 这里只是为了演示的需要，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            // 2. 从SqlSession工厂 SqlSessionFactory中创建一个SqlSession，进行数据库操作
            SqlSession session = sqlMapper.openSession();
            try {
                User user = session.selectOne("UserMapper.getUser", 1);
                System.out.println(user.getId() + "," + user.getUserName());
            } finally {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
