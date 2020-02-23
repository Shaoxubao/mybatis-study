package com.baoge.sqlSession;

import com.baoge.config.Function;
import com.baoge.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/2/23
 */

/**
 * 我们只是希望对指定的接口生成一个对象，使得执行它的时候能运行一句 sql 罢了，
 * 而接口无法直接调用方法，所以这里使用动态代理生成对象，在执行时还是回到 MySqlSession 中调用查询，
 * 最终由 MyExecutor 做 JDBC 查询。这样设计是为了单一职责，可扩展性更强
 */
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlsession;

    private MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration, MySqlSession mySqlsession) {
        this.myConfiguration = myConfiguration;
        this.mySqlsession = mySqlsession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper = myConfiguration.readMapper("conf-sqlmap/UserMapper.xml");
        // 是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }
        List<Function> list = readMapper.getList();
        if (null != list || 0 != list.size()) {
            for (Function function : list) {
                // id是否和接口方法名一样
                if (method.getName().equals(function.getFuncName())) {
                    return mySqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }

}
