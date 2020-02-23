package com.baoge;

import com.baoge.bean.User;
import com.baoge.mapper.UserMapper;
import com.baoge.sqlSession.MySqlSession;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/2/23
 */
public class TestMybatis {

    public static void main(String[] args) {
        MySqlSession sqlSession = new MySqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryById(1);
        System.out.println(user);
    }

}
