package cn.jeeweb.bbs.modules.sys.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.sys.service.ILoginLogService;
import cn.jeeweb.bbs.modules.sys.entity.LoginLog;
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
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.sys.controller
 * @title: 登陆日志控制器
 * @description: 登陆日志控制器
 * @author: sys
 * @date: 2018-09-28 11:35:45
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/sys/login/log")
@ViewPrefix("modules/sys/log")
@RequiresPathPermission("sys:login:log")
public class LoginLogController extends BaseBeanController<LoginLog> {

    @Autowired
    private ILoginLogService loginLogService;


    @GetMapping
    @RequiresMethodPermissions("view")
	public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
		return displayModelAndView("login_list");
	}

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("list")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<LoginLog> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<LoginLog> pagejson = new PageResponse<>(loginLogService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

	@PostMapping("{id}/delete")
    @RequiresMethodPermissions("delete")
	public Response delete(@PathVariable("id") String id) {
	    loginLogService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping("batch/delete")
    @RequiresMethodPermissions("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		loginLogService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

    @RequestMapping("export")
    @RequiresMethodPermissions("export")
    private void export(ModelMap map, Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<LoginLog> entityWrapper = new EntityWrapper<LoginLog>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, LoginLog.class);
        List<LoginLog> userList = loginLogService.listWithNoPage(queryable,entityWrapper);
        String title = "登陆日志";
        ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
        map.put(NormalExcelConstants.DATA_LIST, userList);
        map.put(NormalExcelConstants.CLASS, LoginLog.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put("fileName",title+ "-" + DateUtils.getDateTime());
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}