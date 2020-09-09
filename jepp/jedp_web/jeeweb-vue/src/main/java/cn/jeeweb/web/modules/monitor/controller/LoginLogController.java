package cn.jeeweb.web.modules.monitor.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.common.response.ResponseError;
import cn.jeeweb.web.modules.monitor.entity.LoginLog;
import cn.jeeweb.web.modules.monitor.service.ILoginLogService;
import cn.jeeweb.web.utils.PageRequest;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.sys.controller
 * @title: 登陆日志控制器
 * @description: 登陆日志控制器
 * @author: sys
 * @date: 2018-09-28 11:35:45
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("monitor/login/log")
@ViewPrefix("modules/sys/log")
@RequiresPathPermission("monitor:login:log")
@Log(title = "登陆日志")
public class LoginLogController extends BaseBeanController<LoginLog> {

    @Autowired
    private ILoginLogService loginLogService;


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
        EntityWrapper<LoginLog> entityWrapper = new EntityWrapper<>(LoginLog.class);
        entityWrapper.orderBy("loginTime",false);
        String status = request.getParameter("status");
        if (!StringUtils.isEmpty(status)){
            entityWrapper.eq("status",status);
        }

        // 预处理
        Page pageBean = loginLogService.selectPage(PageRequest.getPage(),entityWrapper);
        FastJsonUtils.print(pageBean);
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

    @GetMapping("export")
    @Log(logType = LogType.EXPORT)
    @RequiresMethodPermissions("export")
    public Response export(HttpServletRequest request) {
        Response response = Response.ok("导出成功");
        try {
            TemplateExportParams params = new TemplateExportParams(
                    "");
            //加入条件
            EntityWrapper<LoginLog> entityWrapper = new EntityWrapper<>(LoginLog.class);
            entityWrapper.orderBy("loginTime",false);
            Page pageBean = loginLogService.selectPage(PageRequest.getPage(),entityWrapper);
            String title = "登陆日志";
            Workbook book = ExcelExportUtil.exportExcel(new ExportParams(
                    title, title, title), LoginLog.class, pageBean.getRecords());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            book.write(bos);
            byte[] bytes = bos.toByteArray();
            String bytesRes = StringUtils.bytesToHexString2(bytes);
            title = title+ "-" + DateUtils.getDateTime();
            response.put("bytes",bytesRes);
            response.put("title",title);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"导出失败");
        }
        return response;
    }
}