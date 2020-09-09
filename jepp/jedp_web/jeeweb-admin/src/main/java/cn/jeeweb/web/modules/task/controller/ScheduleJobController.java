package cn.jeeweb.web.modules.task.controller;

import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.task.entity.ScheduleJob;
import cn.jeeweb.web.modules.task.service.IScheduleJobService;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/task/schedule/job")
@ViewPrefix("modules/task/schedule/job")
@RequiresPathPermission("task:schedule:job")
@Log(title = "计划任务")
public class ScheduleJobController extends BaseBeanController<ScheduleJob> {

	@Autowired
	private IScheduleJobService scheduleJobService;


	@GetMapping
	@RequiresMethodPermissions("view")
	public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
		return displayModelAndView("list");
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })

	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("list")
	public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
						  HttpServletResponse response) throws IOException {
		EntityWrapper<ScheduleJob> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		// 预处理
		QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<ScheduleJob> pagejson = new PageResponse<>(scheduleJobService.list(queryable,entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response,content);
	}

	@GetMapping(value = "add")
	public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("data", new ScheduleJob());
		return displayModelAndView ("edit");
	}

	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(ScheduleJob entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		scheduleJobService.insert(entity);
		return Response.ok("添加成功");
	}

	@GetMapping(value = "{id}/update")
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
							   HttpServletResponse response) {
		ScheduleJob entity = scheduleJobService.selectById(id);
		model.addAttribute("data", entity);
		return displayModelAndView ("edit");
	}

	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	@RequiresMethodPermissions("update")
	public Response update(ScheduleJob entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
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

	@PostMapping(value = "/changeJobStatus")
	@Log(logType = LogType.OTHER,title = "任务状态")
	@RequiresMethodPermissions("change:job:status")
	public Response changeJobStatus(ScheduleJob scheduleJob, HttpServletRequest request,
									HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		String label = "停止";
		if (cmd.equals("start")) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			scheduleJobService.changeStatus(scheduleJob.getId(), cmd);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("任务" + label + "失败" + e.getMessage());
		}
		return 	Response.ok("任务" + label + "成功");
	}

	@PostMapping(value = "/updateCron")
	@Log(logType = LogType.OTHER,title = "任务更新")
	@RequiresMethodPermissions("update:cron")
	public Response updateCron(ScheduleJob scheduleJob) {
		scheduleJobService.updateCron(scheduleJob.getId());
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