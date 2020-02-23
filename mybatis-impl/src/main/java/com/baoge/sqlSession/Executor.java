package com.baoge.sqlSession;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/2/23
 */
public interface Executor {

    public <T> T query(String statement,Object parameter);

}
