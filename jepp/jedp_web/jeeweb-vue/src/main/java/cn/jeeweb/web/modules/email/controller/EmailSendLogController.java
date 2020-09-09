package cn.jeeweb.web.modules.email.controller;

import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.email.entity.EmailSendLog;
import cn.jeeweb.web.modules.email.service.IEmailSendLogService;
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
 * @package cn.jeeweb.web.modules.email.controller
 * @title: 邮件发送日志控制器
 * @description: 邮件发送日志控制器
 * @author: 王存见
 * @date: 2018-09-12 10:58:46
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("/email/sendlog")
@RequiresPathPermission("email:sendlog")
@ViewPrefix("modules/email/sendlog")
@Log(title = "邮件发送日志")
public class EmailSendLogController extends BaseBeanController<EmailSendLog> {

    @Autowired
    private IEmailSendLogService emailSendLogService;


    @GetMapping(value = "list")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void list( HttpServletRequest request) throws IOException {
        //加入条件
        EntityWrapper<EmailSendLog> entityWrapper = new EntityWrapper<>(EmailSendLog.class);
        entityWrapper.orderBy("responseDate",false);
        String subject= request.getParameter("subject");
        if (!StringUtils.isEmpty(subject)){
            entityWrapper.like("subject",subject);
        }
        String email= request.getParameter("email");
        if (!StringUtils.isEmpty(email)){
            entityWrapper.eq("email",email);
        }
        String status=request.getParameter("status");
        if (!StringUtils.isEmpty(status)){
            entityWrapper.eq("status",status);
        }
        // 预处理
        Page pageBean = emailSendLogService.selectPage(PageRequest.getPage(),entityWrapper);
        FastJsonUtils.print(pageBean,EmailSendLog.class,"id,email,subject,content,sendData,sendCode,responseDate,tryNum,msg,status,delFlag,emarks");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        emailSendLogService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        emailSendLogService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }

    @PostMapping(value = "retrySend")
    public Response retrySend(@RequestParam(value = "ids", required = false) String[] ids) {
        try {
            List<String> idList = java.util.Arrays.asList(ids);
            emailSendLogService.retrySend(idList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("重发队列添加失败");
        }
        return Response.ok("重发队列添加成功");
    }
}