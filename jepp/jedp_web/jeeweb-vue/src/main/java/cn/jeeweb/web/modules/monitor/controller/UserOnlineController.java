package cn.jeeweb.web.modules.monitor.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.FastJsonUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sso.service.IOAuthService;
import cn.jeeweb.web.utils.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**   
 * @Title: 在线用户
 * @Description: 在线用户
 * @author jeeweb
 * @date 2017-05-15 08:18:21
 * @version V1.0   
 *
 */
@RestController
@RequestMapping(value = "/monitor/user/online")
@RequiresPathPermission("monitor:user:online")
@Log(title = "在线用户")
public class UserOnlineController extends BaseController{

	@Autowired
	private IOAuthService oAuthService;

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @throws IOException
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })

	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("list")
	public void list() throws IOException {
		// 预处理
		com.baomidou.mybatisplus.plugins.Page pageBean = oAuthService.activePrincipal(PageRequest.getPage());
		FastJsonUtils.print(pageBean);
	}

	@PostMapping("/forceLogout")
	@Log(title = "用户强制退出")
	public Response forceLogout(@RequestParam(value = "ids") String[] ids) {
		for (String id : ids) {
			// 强制退出，不想频繁的请求redis没想好怎么处理
		}
		return Response.ok("强制退出成功");
	}

}
