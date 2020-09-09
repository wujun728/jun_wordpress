package cn.jeeweb.web.config;

import cn.jeeweb.common.query.resolver.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.config
 * @title:
 * @description:  HandlerMethodArgumentResolver
 * @author: 王存见
 * @date: 2018/3/3 17:12
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration
public class ResolverConfig  implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new QueryMethodArgumentResolver());
        argumentResolvers.add(new PageableMethodArgumentResolver());
        argumentResolvers.add(new FormModelMethodArgumentResolver());
        argumentResolvers.add(new RequestJsonParamMethodArgumentResolver());
        argumentResolvers.add(new PropertyPreFilterMethodArgumentResolver());
    }

}


