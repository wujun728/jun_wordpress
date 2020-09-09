package cn.jeeweb.web.modules.task.controller;

import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.task.entity.ScheduleJobLog;
import cn.jeeweb.web.modules.task.service.IScheduleJobLogService;
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
import cn.jeeweb.web.utils.PageRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.task.controller
 * @title: 任务日志控制器
 * @description: 任务日志控制器
 * @author: 王存见
 * @date: 2018-09-17 14:25:19
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("/task/schedule/job/log")
@ViewPrefix("modules/task/schedule/job")
@RequiresPathPermission("task:schedule:joblog")
@Log(title = "计划任务日志")
public class ScheduleJobLogController extends BaseBeanController<ScheduleJobLog> {

    @Autowired
    private IScheduleJobLogService scheduleJobLogService;

    @GetMapping(value = "list")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void list( HttpServletRequest request) throws IOException {
        //加入条件
        EntityWrapper<ScheduleJobLog> entityWrapper = new EntityWrapper<>(ScheduleJobLog.class);
        entityWrapper.orderBy("createTime",false);
        String jobName= request.getParameter("jobName");
        if (!StringUtils.isEmpty(jobName)){
            entityWrapper.like("jobName",jobName);
        }
        String executeClass= request.getParameter("executeClass");
        if (!StringUtils.isEmpty(executeClass)){
            entityWrapper.eq("executeClass",executeClass);
        }
        String status=request.getParameter("status");
        if (!StringUtils.isEmpty(status)){
            entityWrapper.eq("status",status);
        }
        // 预处理
        Page pageBean = scheduleJobLogService.selectPage(PageRequest.getPage(),entityWrapper);
        FastJsonUtils.print(pageBean,ScheduleJobLog.class,"id,jobName,executeClass,jobGroup,methodName,methodParams,jobMessage,status,exceptionInfo,createTime");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        scheduleJobLogService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        scheduleJobLogService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }
}