package cn.jeeweb.generator.config;

import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.DictUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

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
public class DictConfig implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        //初始化页面数据字典
        List<Dict> sfList = new ArrayList<>();
        sfList.add(new Dict("是", "1"));
        sfList.add(new Dict("否", "0"));
        DictUtils.putDict("sf", sfList);
    }
}
