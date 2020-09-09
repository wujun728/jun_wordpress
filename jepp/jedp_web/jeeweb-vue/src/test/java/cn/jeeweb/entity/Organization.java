package cn.jeeweb.entity;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package PACKAGE_NAME
 * @title:
 * @description: Cache工具类
 * @author: 王存见
 * @date: 2018/5/23 11:34
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class Organization implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
