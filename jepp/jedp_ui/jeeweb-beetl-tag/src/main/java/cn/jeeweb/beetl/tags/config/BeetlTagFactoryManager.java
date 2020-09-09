package cn.jeeweb.beetl.tags.config;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.SpringBeanTagFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.tags.config
 * @title:
 * @description: 标签处理
 * @author: 王存见
 * @date: 2018/8/7 15:10
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Component
public class BeetlTagFactoryManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(name = "tagFactorys")
    public Map<String, TagFactory> tagFactorys() {
        Map<String, TagFactory> tags = new HashMap<>();
        Map<String, Tag> beans = applicationContext.getBeansOfType(Tag.class);
        for (String beanName : beans.keySet()) {
            // 读取自定义标签名
            BeetlTagName tagAnno = beans.get(beanName).getClass().getAnnotation(BeetlTagName.class);
            String tagName = tagAnno != null ? tagAnno.value() : beanName;
            tags.put(tagName, toSpringBeanTagFactory(beanName));
        }
        return tags;
    }

    private SpringBeanTagFactory toSpringBeanTagFactory(String beanName) {
        SpringBeanTagFactory springBeanTagFactory = new SpringBeanTagFactory();
        springBeanTagFactory.setApplicationContext(applicationContext);
        springBeanTagFactory.setName(beanName);
        return springBeanTagFactory;
    }
}
