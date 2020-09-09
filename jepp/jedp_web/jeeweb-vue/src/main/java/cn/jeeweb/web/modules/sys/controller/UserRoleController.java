package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sys.entity.UserRole;
import cn.jeeweb.web.modules.sys.service.IUserRoleService;
import cn.jeeweb.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.gzst.gov.cn
 *
 * @version V1.0
 * @package cn.gov.gzst.upms.controller
 * @title: 用户角色控制器
 * @description: 用户角色控制器
 * @author: 王存见
 * @date: 2018-05-06 18:46:48
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@RestController
@RequestMapping("/sys/user/role")
@RequiresPathPermission("sys:user:role")
@Log(title = "用户管理")
public class UserRoleController extends BaseBeanController<UserRole> {

    @Autowired
    private IUserRoleService userRoleService;


    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param userid
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "{userid}/roleIds")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public List<String> userRoleIds(@PathVariable("userid") String userid, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        List<String> roleIdList=new ArrayList<String>();
        List<UserRole> userRoleList=userRoleService.selectList(new EntityWrapper<>(UserRole.class).eq("userId", userid));
        for (UserRole userRole: userRoleList) {
            roleIdList.add(userRole.getRoleId());
        }
       return roleIdList;
    }

    /**
     * 新增关系
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("{userId}/insertByUserId")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response insertByUserId(@PathVariable("userId") String userId, @RequestParam("roleIds") String[] roleIds) {
        for (String roleId:roleIds) {
            UserRole userRole=new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleService.insertByRoleId(userId,roleId);
        }
        UserUtils.clearCache();
        return Response.ok("添加成功");
    }

    /**
     * 删除关系
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("{userId}/deleteByUserId")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response deleteByUserId(@PathVariable("userId") String userId, @RequestParam("roleIds") String roleIds) {
        EntityWrapper<UserRole> entityWrapper=new EntityWrapper<UserRole>(UserRole.class);
        entityWrapper.eq("userId",userId);
        entityWrapper.in("roleId",roleIds);
        userRoleService.delete(entityWrapper);
        UserUtils.clearCache();
        return Response.ok("删除成功");
    }

}