package cn.jeeweb.web.config;

import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.fastjson.FastjsonUnXssFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.spring
 * @title:
 * @description: WEB 初始化相关配置
 * @author: 王存见
 * @date: 2018/2/22 12:35
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */

@ControllerAdvice
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();//4
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        FastjsonUnXssFilter jsonUnFilter = new FastjsonUnXssFilter();
        fastJsonConfig.setSerializeFilters(jsonUnFilter);
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaTypes);
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(converter);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}