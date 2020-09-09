package cn.jeeweb.bbs.modules.task.utils;


import cn.jeeweb.bbs.modules.task.entity.ScheduleJob;

public class ScheduleJobUtils {
     public static cn.jeeweb.common.quartz.data.ScheduleJob entityToData(ScheduleJob scheduleJobEntity){
    	 cn.jeeweb.common.quartz.data.ScheduleJob scheduleJob=new cn.jeeweb.common.quartz.data.ScheduleJob();
		 scheduleJob.setJobId(scheduleJobEntity.getId());
    	 scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
    	 scheduleJob.setDescription(scheduleJobEntity.getDescription());
    	 scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
    	 scheduleJob.setJobName(scheduleJobEntity.getJobName());
		 scheduleJob.setLoadWay(scheduleJobEntity.getLoadWay());
    	 scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
    	 scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
    	 scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		 scheduleJob.setMethodParams(scheduleJobEntity.getMethodParams());
		 scheduleJob.setMisfirePolicy(scheduleJobEntity.getMisfirePolicy());
    	 scheduleJob.setExecuteClass(scheduleJobEntity.getExecuteClass());
    	 return scheduleJob;
     }
}
