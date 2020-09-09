package cn.jeeweb.beetl.tags;

import cn.jeeweb.beetl.tags.exception.BeetlTagException;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.beetl.tags
 * @title:
 * @description: 动态数据整理
 * @author: 王存见
 * @date: 2018/8/8 19:29
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public interface DynamicAttributes {
    void setDynamicAttribute(String localName, Object value) throws BeetlTagException;
}
