package cn.jeeweb.web.modules.sys.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.jeeweb.common.http.DuplicateValid;
import cn.jeeweb.common.http.ValidResponse;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.common.response.ResponseError;
import cn.jeeweb.web.modules.monitor.entity.LoginLog;
import cn.jeeweb.web.modules.sys.entity.*;
import cn.jeeweb.web.modules.sys.service.*;
import cn.jeeweb.web.utils.PageRequest;
import cn.jeeweb.web.utils.UserUtils;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("/sys/user")
@ViewPrefix("modules/sys/user")
@RequiresPathPermission("sys:user")
@Log(title = "用户管理")
public class UserController extends BaseBeanController<User> {
	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRoleService userRoleService;

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IUserOrganizationService userOrganizationService;

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("list")
	public void list(HttpServletRequest request) throws IOException {
		EntityWrapper<User> entityWrapper = new EntityWrapper<>(entityClass);
		String realname= request.getParameter("realname");
		if (!StringUtils.isEmpty(realname)){
			entityWrapper.like("realname",realname);
		}
		String username= request.getParameter("username");
		if (!StringUtils.isEmpty(username)){
			entityWrapper.like("username",username);
		}
		String phone= request.getParameter("phone");
		if (!StringUtils.isEmpty(phone)){
			entityWrapper.like("phone",phone);
		}
		// 预处理
		Page pageBean = userService.selectPage(PageRequest.getPage(),entityWrapper);
		FastJsonUtils.print(pageBean);
	}

	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(User entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		userService.insert(entity);
		//保存之后
		afterSave(entity,request);
		return Response.ok("添加成功");
	}

	@GetMapping(value = "{id}/update")
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
							   HttpServletResponse response) {
		User user = userService.selectById(id);
		model.addAttribute("data", user);
		// 查询所有的角色List
		List<Role> allRoles = roleService.selectList(new EntityWrapper<Role>());
		request.setAttribute("allRoles", allRoles);
		if (!StringUtils.isEmpty(user.getId())) {
			// 查找关联角色
			List<Role> userRoles = roleService.findListByUserId(user.getId());
			List<String> roleIdList = new ArrayList<>();
			for (Role role: userRoles) {
				roleIdList.add(role.getId());
			}
			request.setAttribute("roleIdList", roleIdList);
			List<Organization> organizations = organizationService.findListByUserId(user.getId());
			String organizationIds = "";
			String organizationNames = "";
			for (Organization organization : organizations) {
				if (!StringUtils.isEmpty(organizationIds)) {
					organizationIds += ",";
					organizationNames += ",";
				}
				String organizationId = organization.getId();
				organizationIds += organizationId;
				organizationNames += organization.getName();

			}
			request.setAttribute("organizationIds", organizationIds);
			request.setAttribute("organizationNames", organizationNames);
		}

		return displayModelAndView ("edit");
	}

	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	@RequiresMethodPermissions("update")
	public Response update(User entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		userService.insertOrUpdate(entity);
		//保存之后
		afterSave(entity,request);
		return Response.ok("更新成功");
	}

	@PostMapping("{id}/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response delete(@PathVariable("id") String id) {
		userService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping("batch/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		userService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

	@PostMapping(value = "{id}/changePassword")
	@Log(logType = LogType.OTHER,title = "修改成功")
	@RequiresMethodPermissions("change:password")
	public Response changePassword(@PathVariable("id") String id, HttpServletRequest request,
								   HttpServletResponse response) {
		String password = request.getParameter("password");
		userService.changePassword(id, password);
		return Response.ok("密码修改成功");
	}

	@GetMapping(value = "{id}/avatar")
	public ModelAndView avatar(@PathVariable("id") String id, Model model, HttpServletRequest request,
						 HttpServletResponse response) {
		User user = userService.selectById(id);
		model.addAttribute("data", user);
		return displayModelAndView("avatar");
	}

	@PostMapping(value = "{id}/avatar")
	@Log(logType = LogType.OTHER,title = "修改头像")
	@RequiresMethodPermissions("avatar")
	public Response avatar(User user, HttpServletRequest request, HttpServletResponse response) {
		try {
			User oldUser = userService.selectById(user.getId());
			BeanUtils.copyProperties(user,oldUser);
			userService.insertOrUpdate(oldUser);
			String currentUserId = UserUtils.getUser().getId();
			if (currentUserId.equals(user.getId())) {
				UserUtils.clearCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("头像修改失败");
		}
		return Response.ok("头像修改成功");
	}

	public void afterSave(User entity, HttpServletRequest request) {
		// 删除角色关联
		String[] roleIdList = request.getParameterValues("roleIdList");
		if (roleIdList != null && roleIdList.length > 0) {
			userRoleService.delete(new EntityWrapper<UserRole>(UserRole.class).eq("userId", entity.getId()));
			List<UserRole> userRoleList = new ArrayList<UserRole>();
			for (String roleid : roleIdList) {
				UserRole userRole = new UserRole();
				userRole.setUserId(entity.getId());
				userRole.setRoleId(roleid);
				userRoleList.add(userRole);
			}
			userRoleService.insertBatch(userRoleList);
		}

		// 删除部门关联
		String organizationIdListStr = request.getParameter("organizationIds");
		if (!StringUtils.isEmpty(organizationIdListStr)) {
			String[] organizationIdList = organizationIdListStr.split(",");
			if (organizationIdList != null && organizationIdList.length > 0) {
				userOrganizationService.delete(new EntityWrapper<UserOrganization>(UserOrganization.class).eq("userId", entity.getId()));
				List<UserOrganization> userOrganizationList = new ArrayList<UserOrganization>();
				for (String organizationId : organizationIdList) {
					UserOrganization userOrganization = new UserOrganization();
					userOrganization.setUserId(entity.getId());
					userOrganization.setOrganizationId(organizationId);
					userOrganizationList.add(userOrganization);
				}
				userOrganizationService.insertBatch(userOrganizationList);
			}
		}
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
			EntityWrapper<User> entityWrapper = new EntityWrapper<>(User.class);
			// 子查询
			String organizationid = request.getParameter("organizationid");
			if (!StringUtils.isEmpty(organizationid)) {
				entityWrapper.eq("uo.organization_id", organizationid);
			}
			Page pageBean = userService.selectPage(PageRequest.getPage(),entityWrapper);
			String title = "用户信息";
			Workbook book = ExcelExportUtil.exportExcel(new ExportParams(
					title, title, title), User.class, pageBean.getRecords());
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

	@PostMapping(value = "validate")
	public ValidResponse validate(DuplicateValid duplicateValid, HttpServletRequest request) {
		ValidResponse validResponse = new ValidResponse();
		Boolean valid = Boolean.FALSE;
		try {
			EntityWrapper<User> entityWrapper = new EntityWrapper<User>(entityClass);
			valid = userService.doValid(duplicateValid,entityWrapper);
			if (valid) {
				validResponse.setStatus("y");
				validResponse.setInfo("验证通过!");
			} else {
				validResponse.setStatus("n");
				if (!StringUtils.isEmpty(duplicateValid.getErrorMsg())) {
					validResponse.setInfo(duplicateValid.getErrorMsg());
				} else {
					validResponse.setInfo("当前信息重复!");
				}
			}
		} catch (Exception e) {
			validResponse.setStatus("n");
			validResponse.setInfo("验证异常，请检查字段是否正确!");
		}
		return validResponse;
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	@GetMapping(value = "info")
	public Response info() {
		Response response = Response.ok();
		User user= UserUtils.getUser();
		if (user==null){
			return Response.error("获取失败");
		}else{
			response.putObject(user,"id,username,realname,phone,portrait,email,status");
			//获取角色
			//response.put("roles",UserUtils.getRoleStringList());
			List<String> roles = new ArrayList<>();
			roles.add("admin");
			response.put("roles",roles);
		}
		return response;
	}

	/**
	 * 更新用户信息
	 *
	 * @param user
	 * @param request
	 * @return
	 */
	@PostMapping("my/update")
	@Log(logType = LogType.UPDATE, title = "用户更新")
	public Response myUpdate(User user,HttpServletRequest request) {
		String userId = UserUtils.getUser().getId();
		User oldUser =  userService.selectById(userId);
		// 验证错误
		BeanUtils.copyProperties(user,oldUser);
		userService.insertOrUpdate(oldUser);
		return Response.ok("更新成功");
	}

	/**
	 * 更新用户信息
	 *
	 * @param oldPassword
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("my/changePassword")
	@Log(logType = LogType.OTHER, title = "用户修改密码")
	public Response myChangePassword(String oldPassword,
									 String password,HttpServletRequest request) {
		String userId = UserUtils.getUser().getId();
		if (userService.checkPassword(userId,oldPassword)){
			userService.changePassword(userId,password);
		}else{
			return Response.error("原密码错误");
		}
		return Response.ok("密码修改成功");
	}
}