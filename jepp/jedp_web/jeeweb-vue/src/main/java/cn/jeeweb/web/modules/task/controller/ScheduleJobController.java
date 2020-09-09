package cn.jeeweb.web.modules.task.controller;

import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.task.service.IScheduleJobService;
import cn.jeeweb.web.modules.task.entity.ScheduleJob;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.utils.PageRequest;
import com.baomidou.mybatisplus.plugins.Page;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/task/schedule/job")
@ViewPrefix("modules/task/schedule/job")
@RequiresPathPermission("task:schedule:job")
@Log(title = "计划任务")
public class ScheduleJobController extends BaseBeanController<ScheduleJob> {

	@Autowired
	private IScheduleJobService scheduleJobService;


	@GetMapping(value = "list")
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("list")
	public void list( HttpServletRequest request) throws IOException {
		//加入条件
		EntityWrapper<ScheduleJob> entityWrapper = new EntityWrapper<>(ScheduleJob.class);
		entityWrapper.orderBy("createDate",false);
		String jobName= request.getParameter("jobName");
		if (!StringUtils.isEmpty(jobName)){
			entityWrapper.like("jobName",jobName);
		}
		// 预处理
		Page pageBean = scheduleJobService.selectPage(PageRequest.getPage(),entityWrapper);
		FastJsonUtils.print(pageBean,ScheduleJob.class,"id,jobName,cronExpression,executeClass,methodName,methodParams,misfirePolicy,loadWay,isConcurrent,description,jobStatus,jobGroup");
	}

	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(ScheduleJob entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		if (!CronExpression.isValidExpression(entity.getCronExpression())) {
			return Response.error("cron表达式格式不对");
		}
		scheduleJobService.insert(entity);
		return Response.ok("添加成功");
	}

	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	@RequiresMethodPermissions("update")
	public Response update(ScheduleJob entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		if (!CronExpression.isValidExpression(entity.getCronExpression())) {
			return Response.error("cron表达式格式不对");
		}
		scheduleJobService.insertOrUpdate(entity);
		return Response.ok("更新成功");
	}

	@PostMapping("{id}/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response delete(@PathVariable("id") String id) {
		scheduleJobService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping(value = "/saveScheduleJob")
	public Response saveScheduleJob(ScheduleJob scheduleJob, HttpServletRequest request, HttpServletResponse response) {
		if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
			return Response.error("cron表达式格式不对");
		}
		try {
			if (ObjectUtils.isNullOrEmpty(scheduleJob.getId())) {
				scheduleJobService.insert(scheduleJob);
			} else {
				// FORM NULL不更新
				ScheduleJob oldEntity = scheduleJobService.selectById(scheduleJob.getId());
				BeanUtils.copyProperties(scheduleJob,oldEntity);
				scheduleJobService.insertOrUpdate(oldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("保存失败"+e.getMessage());
		}
		return Response.ok("保存成功");
	}

	@PostMapping("batch/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		scheduleJobService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

	@PostMapping(value = "{id}/changeJobStatus")
	@Log(logType = LogType.OTHER,title = "任务状态")
	@RequiresMethodPermissions("change:job:status")
	public Response changeJobStatus(@PathVariable("id") String id, HttpServletRequest request,
									HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		String label = "停止";
		if (cmd.equals("start")) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			scheduleJobService.changeStatus(id, cmd);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("任务" + label + "失败" + e.getMessage());
		}
		return 	Response.ok("任务" + label + "成功");
	}

	@PostMapping(value = "{id}/updateCron")
	@Log(logType = LogType.OTHER,title = "任务更新")
	@RequiresMethodPermissions("update:cron")
	public Response updateCron(@PathVariable("id") String id) {
		scheduleJobService.updateCron(id);
		return Response.ok("任务更新成功");
	}

	@PostMapping(value = "/runAJobNow")
	@Log(logType = LogType.OTHER,title = "执行一次")
	@RequiresMethodPermissions("run:ajob:now")
	public Response runAJobNow(ScheduleJob scheduleJob, HttpServletRequest request,
							   HttpServletResponse response) {
	    scheduleJobService.runAJobNow(scheduleJob.getId());
		return Response.ok("任务启动成功");
	}

	/**
	 * 刷新任务
	 * @return
	 */
	@PostMapping(value = "/refreshJob")
	@Log(logType = LogType.OTHER,title = "刷新任务")
	@RequiresMethodPermissions("refresh:job")
	public Response refreshJob() {
		scheduleJobService.refreshTask();
		return Response.ok("刷新任务成功");
	}
}