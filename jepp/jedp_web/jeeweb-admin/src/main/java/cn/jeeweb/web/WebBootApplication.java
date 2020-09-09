package cn.jeeweb.web;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web
 * @title: Web启动入口
 * @description: Web启动入口
 * @author: 王存见
 * @date: 2018/5/22 14:56
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@EnableSwagger2Doc
@EnableSwagger2
@ComponentScan({"cn.jeeweb.common.quartz.config","cn.jeeweb.common.oss","cn.jeeweb.common.sms","cn.jeeweb.ui.tags","cn.jeeweb.ui","cn.jeeweb.beetl.tags","cn.jeeweb.web"})
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
// @SpringBootApplication
public class WebBootApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebBootApplication.class, args);
    }
}

