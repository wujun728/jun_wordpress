package cn.jeeweb.bbs;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web
 * @title: Web启动入口
 * @description: Web启动入口
 * @author: 王存见 https://blog.csdn.net/u012943767/article/details/79360748
 * @date: 2018/5/22 14:56
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@EnableSwagger2Doc
@ComponentScan({"cn.jeeweb.common.quartz.config","cn.jeeweb.common.oss","cn.jeeweb.common.sms","cn.jeeweb.ui.tags","cn.jeeweb.ui","cn.jeeweb.beetl.tags","cn.jeeweb.bbs"})
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class BbsBootApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BbsBootApplication.class, args);
    }
}