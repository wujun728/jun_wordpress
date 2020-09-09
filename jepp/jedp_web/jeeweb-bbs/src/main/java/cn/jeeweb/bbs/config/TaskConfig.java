package cn.jeeweb.bbs.config;

import cn.jeeweb.common.disruptor.TaskHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.dataact.jeeweb.modules.config
 * @title:
 * @description: 邮件发送配置
 * @author: 王存见
 * @date: 2018/3/1 16:06
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration
public class TaskConfig {

    @Bean
    public TaskHelper taskHelper(){
        TaskHelper taskHelper=  new TaskHelper();
        taskHelper.setHandlerCount(3);
        taskHelper.setBufferSize(1024);
        return taskHelper;
    }

}
