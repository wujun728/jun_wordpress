package cn.jeeweb.generator;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.generator
 * @title:
 * @description: 启动入口
 * @author: 王存见
 * @date: 2018/8/14 10:25
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class GeneratorBootServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GeneratorBootApplication.class);
    }
}