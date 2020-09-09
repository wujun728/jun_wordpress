package cn.jeeweb.web.modules.oss.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.common.bean.ResponseError;
import cn.jeeweb.web.modules.oss.entity.Attachment;
import cn.jeeweb.web.modules.oss.service.IAttachmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.gov.gzst.oss.controller
 * @title: 附件管理控制器
 * @description: 附件管理控制器
 * @author: 王存见
 * @date: 2018-04-25 14:25:55
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Controller
@RequestMapping("${jeeweb.admin.url.prefix}/oss/attachment")
@ViewPrefix("modules/oss/attachment")
@RequiresPathPermission("oss:attachment")
public class AttachmentController extends BaseBeanController<Attachment> {

    @Autowired
    private IAttachmentService attachmentService;



    //@RequiresMethodPermissions("list")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return display("list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<Attachment> entityWrapper = new EntityWrapper<Attachment>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        //加入条件
        entityWrapper.orderBy("uploadTime",false);
        PageResponse<Attachment> pagejson = new PageResponse<Attachment>(attachmentService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    @ResponseBody
    public Response delete(@PathVariable("id") String id) {
        attachmentService.deleteById(id);
        return Response.ok();
    }

    @RequestMapping(value = "batch/delete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response batchDelete(@RequestParam(value = "ids", required = false) String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        attachmentService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }

}