package cn.jeeweb.common.quartz.callback;

import cn.jeeweb.common.quartz.data.ScheduleJob;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.common.quartz.callback
 * @title:
 * @description: 执行回掉
 * @author: 王存见
 * @date: 2018/9/17 14:08
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public interface QuartzExecuteCallback {

    /**
     * 执行开始
     * @param scheduleJob
     */
    void onStart(ScheduleJob scheduleJob);
    /**
     * 当执行成功
     * @param scheduleJob
     * @param message
     */
    void onSuccess(ScheduleJob scheduleJob,String message);

    /**
     * 执行失败
     * @param scheduleJob
     * @param e
     * @param message
     */
    void onFailure(ScheduleJob scheduleJob,Exception e,String message);
}
