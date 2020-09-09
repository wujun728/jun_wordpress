package cn.jeeweb;

import cn.jeeweb.common.utils.StringUtils;

import java.util.Random;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb
 * @title:
 * @description: Cache工具类
 * @author: 王存见
 * @date: 2018/9/7 17:34
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class TestRand {
    public static void main(String[] args) {
        Random ran1 = new Random();
        System.out.println("使用种子为10的Random对象生成[0,10)内随机整数序列: ");
        System.out.print(ran1.nextInt(13) + " ");
    }
}
