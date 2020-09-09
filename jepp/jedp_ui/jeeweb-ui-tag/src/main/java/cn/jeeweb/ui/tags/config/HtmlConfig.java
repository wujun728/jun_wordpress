package cn.jeeweb.ui.tags.config;

import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.ui.tags.html.builder.DefaultHtmlComponentBuilder;
import cn.jeeweb.ui.tags.html.manager.HtmlComponentManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.jeeweb.modules.config
 * @title:
 * @description: HTML初始化
 * @author: 王存见
 * @date: 2018/3/3 15:06
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration
public class HtmlConfig implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        SpringContextHolder.getApplicationContext()
                .getBean(HtmlComponentManager.class).init();
    }
    /**
     * HTML 组建加载器
     * @return
     */
    @Bean
    public DefaultHtmlComponentBuilder defaultHtmlComponentBuilder(){
        DefaultHtmlComponentBuilder defaultHtmlComponentBuilder=new DefaultHtmlComponentBuilder();
        defaultHtmlComponentBuilder.setFileNames(new String[]{"classpath:/tagmapper/*-html-component.xml"});
        return defaultHtmlComponentBuilder;
    }

    @Bean
    public HtmlComponentManager htmlComponentManager( DefaultHtmlComponentBuilder defaultHtmlComponentBuilder){
        HtmlComponentManager htmlComponentManager=new HtmlComponentManager();
        htmlComponentManager.setDynamicStatementBuilder(defaultHtmlComponentBuilder);
        return htmlComponentManager;
    }
}
