package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.common.helper.VueTreeHelper;
import cn.jeeweb.web.common.response.ResponseError;
import cn.jeeweb.web.modules.sys.entity.Menu;
import cn.jeeweb.web.modules.sys.entity.Role;
import cn.jeeweb.web.utils.PageRequest;
import cn.jeeweb.web.utils.UserUtils;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sys.entity.RoleMenu;
import cn.jeeweb.web.modules.sys.service.IMenuService;
import cn.jeeweb.web.modules.sys.service.IRoleMenuService;
import cn.jeeweb.web.modules.sys.service.IRoleService;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
@RequestMapping("/sys/role")
@ViewPrefix("modules/sys/role")
//@RequiresPathMenu("sys:role")
@Log(title = "角色管理")
public class RoleController extends BaseBeanController<Role> {

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IRoleMenuService roleMenuService;

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @throws IOException
	 */
	@GetMapping(value = "list")
	@Log(logType = LogType.SELECT)
	//@RequiresMethodMenus("list")
	public void list( HttpServletRequest request) throws IOException {
		//加入条件
		EntityWrapper<Role> entityWrapper = new EntityWrapper<>(Role.class);
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
		Page pageBean = roleService.selectPage(PageRequest.getPage(),entityWrapper);
		FastJsonUtils.print(pageBean,Role.class,"id,name,code,isSys,usable");
	}

	/**
	 * 获取可用的用户列表
	 *
	 * @throws IOException
	 */
	@GetMapping(value = "usable/list")
	public List<Role> usableLst() throws IOException {
		EntityWrapper<Role> entityWrapper = new EntityWrapper<Role>(Role.class);
		entityWrapper.orderBy("createDate",false);
		List<Role> usableLst = roleService.selectList(entityWrapper);
		return usableLst;
	}

	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	//@RequiresMethodMenus("add")
	public Response add(Role entity, BindingResult result) {
		// 验证错误
		this.checkError(entity,result);
		roleService.insert(entity);
		return Response.ok("添加成功");
	}

	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	//@RequiresMethodMenus("update")
	public Response update(Role entity, BindingResult result) {
		// 验证错误
		this.checkError(entity,result);
		roleService.insertOrUpdate(entity);
		return Response.ok("更新成功");
	}

	@PostMapping("{id}/delete")
	@Log(logType = LogType.DELETE)
	//@RequiresMethodMenus("delete")
	public Response delete(@PathVariable("id") String id) {
		roleService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping("batch/delete")
	@Log(logType = LogType.DELETE)
	//@RequiresMethodMenus("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		roleService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

	/**
	 * 通过用户ID获得角色
	 * @param uid
	 * @return
	 */
	@PostMapping(value = "{uid}/findListByUserId")
	public List<Role> findListByUserId(@PathVariable("uid") String uid) {
		try {
			return roleService.findListByUserId(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@GetMapping(value = "{roleId}/menu")
	public void menu(@PathVariable("roleId") String roleId,
						   HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> dataMap = new HashMap<>();
		EntityWrapper<Menu> entityWrapper = new EntityWrapper<>(Menu.class);
		entityWrapper.setTableAlias("t.");
		//加入条件
		List<Menu> treeNodeList = menuService.selectTreeList(entityWrapper);
		List<VueTreeHelper.VueTreeNode> vueTreeNodes = VueTreeHelper.create().sort(treeNodeList);
		dataMap.put("menus", vueTreeNodes);
		// 获得选择的
		List<Menu> menuList = menuService.findMenuByRoleId(roleId);
		List<String> menuIdList = new ArrayList<>();
		for (Menu menu:menuList) {
			menuIdList.add(menu.getId());
		}
		dataMap.put("selectMenuIds", menuIdList);
		String content = JSON.toJSONString(dataMap);
		StringUtils.printJson(response, content);
	}

	@PostMapping(value = "/setMenu")
	@Log(logType = LogType.OTHER,title = "菜单授权")
	//@RequiresMethodMenus("authMenu")
	public Response setMenu(@RequestParam("roleId") String roleId,
										@RequestParam("menuIds") String menuIds) {
		try {
			// 权限设置
			roleMenuService.setMenu(roleId,menuIds);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
		}
		return Response.ok("保存成功");
	}
}