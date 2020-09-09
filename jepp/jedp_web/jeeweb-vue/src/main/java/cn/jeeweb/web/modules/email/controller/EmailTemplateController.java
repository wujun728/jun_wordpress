package cn.jeeweb.web.modules.email.controller;

import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.email.entity.EmailTemplate;
import cn.jeeweb.web.modules.email.service.IEmailTemplateService;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.email.controller
 * @title: 邮件模板控制器
 * @description: 邮件模板控制器
 * @author: 王存见
 * @date: 2018-09-12 10:59:18
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("/email/template")
@RequiresPathPermission("email:template")
@ViewPrefix("modules/email/template")
@Log(title = "邮件模板")
public class EmailTemplateController extends BaseBeanController<EmailTemplate> {

    @Autowired
    private IEmailTemplateService emailTemplateService;

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @throws IOException
     */
    @GetMapping(value = "list")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void list( HttpServletRequest request) throws IOException {
        //加入条件
        EntityWrapper<EmailTemplate> entityWrapper = new EntityWrapper<>(EmailTemplate.class);
        entityWrapper.orderBy("createDate",false);
        String code= request.getParameter("code");
        if (!StringUtils.isEmpty(code)){
            entityWrapper.like("code",code);
        }
        String name=request.getParameter("name");
        if (!StringUtils.isEmpty(name)){
            entityWrapper.like("name",name);
        }
        // 预处理
        Page pageBean = emailTemplateService.selectPage(PageRequest.getPage(),entityWrapper);
        FastJsonUtils.print(pageBean,EmailTemplate.class,"id,name,code,templateSubject,templateContent");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(EmailTemplate entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        String templateCode = StringUtils.randomString(10);
        entity.setCode(templateCode);
        emailTemplateService.insert(entity);
        return Response.ok("添加成功");
    }


    @PostMapping("{id}/update")
    @RequiresMethodPermissions("add")
    @Log(logType = LogType.UPDATE)
    public Response update(EmailTemplate entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        emailTemplateService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        emailTemplateService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        emailTemplateService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }
}