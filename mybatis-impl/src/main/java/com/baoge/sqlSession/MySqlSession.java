package com.baoge.sqlSession;

import java.lang.reflect.Proxy;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/2/23
 */

/**
 * ——> MyConfiguration ——> MySqlSession ——> MyExecutor  ……> mySql
 *                                                    ↓               ↑
 *                                                    MyMapperProxy ——
 *
 * Session 到底是什么呢？一个 Session 仅拥有一个对应的数据库连接。类似于一个前段请求 Request，
 * 它可以直接调用 exec(SQL) 来执行 SQL 语句。从流程图中的箭头可以看出，
 * MySqlSession 的成员变量中必须得有 MyExecutor 和 MyConfiguration 去集中做调配，
 * 箭头就像是一种关联关系。我们自己的 MySqlSession 将有一个 getMapper 方法，
 * 然后使用动态代理生成对象后，就可以做数据库的操作了。
 */
public class MySqlSession {

    private Executor executor = new MyExecutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        // 动态代理调用
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new MyMapperProxy(myConfiguration, this));
    }
}
