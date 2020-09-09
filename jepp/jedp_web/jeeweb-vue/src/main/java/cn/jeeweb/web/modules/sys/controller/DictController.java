package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sys.entity.DictGroup;
import cn.jeeweb.web.modules.sys.entity.Dict;
import cn.jeeweb.web.modules.sys.service.IDictGroupService;
import cn.jeeweb.web.modules.sys.service.IDictService;
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
import cn.jeeweb.web.utils.DictUtils;
import cn.jeeweb.web.utils.PageRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
 * @package cn.jeeweb.web.modules.sys.controller
 * @title: 消息模版控制器
 * @description: 消息模版控制器
 * @author: 王存见
 * @date: 2018-09-03 15:10:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("/sys/dict")
@ViewPrefix("modules/sys/dict")
@RequiresPathPermission("sys:dict")
@Log(title = "字典管理")
public class DictController extends BaseBeanController<Dict> {

	@Autowired
	private IDictService dictService;

	/**
	 * 字典
	 * @return
	 */
	@GetMapping(value = "")
	public Response get() {
		Response response = new Response();
		try {
			//放入数据字典
			response.putObject(DictUtils.getDict());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("获取失败");
		}
		return response;
	}
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
		EntityWrapper<Dict> entityWrapper = new EntityWrapper<>(Dict.class);
		entityWrapper.orderBy("sort",true);
		String keyword=request.getParameter("keyword");
		String gid=request.getParameter("gid");
		if (!StringUtils.isEmpty(gid)&&!StringUtils.isEmpty(keyword)){
			entityWrapper.eq("gid",gid).andNew().like("label",keyword).or().like("value",keyword);
		}else if(!StringUtils.isEmpty(gid)){
			entityWrapper.eq("gid",gid);
		}

		// 预处理
		Page pageBean = dictService.selectPage(PageRequest.getPage(),entityWrapper);
		FastJsonUtils.print(pageBean);
	}

	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(Dict entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		dictService.insert(entity);
		return Response.ok("添加成功");
	}

	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	@RequiresMethodPermissions("add")
	public Response update(Dict entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		dictService.insertOrUpdate(entity);
		return Response.ok("更新成功");
	}

	@PostMapping("{id}/delete")
	@Log(logType = LogType.DELETE)
	public Response delete(@PathVariable("id") String id) {
		dictService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping("batch/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		dictService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

}