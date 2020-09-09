package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.security.shiro.session.SessionDAO;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sys.entity.UserOnline;
import cn.jeeweb.web.security.shiro.session.mgt.OnlineSession;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**   
 * @Title: 在线用户
 * @Description: 在线用户
 * @author jeeweb
 * @date 2017-05-15 08:18:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping(value = "${jeeweb.admin.url.prefix}/sys/online")
@ViewPrefix("modules/sys/online")
@RequiresPathPermission("sys:online")
public class UserOnlineController extends BaseController{

	@Autowired
	private SessionDAO sessionDAO;

	public UserOnlineController() {
	}


	@RequiresMethodPermissions("list")
	@GetMapping
	public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
		// 预处理
		Collection<Session> sessionList=sessionDAO.getActiveSessions(false,null,null);
		List<UserOnline> onlineSessionList=new ArrayList<UserOnline>();
		for (Session session:sessionList) {
			UserOnline userOnline=UserOnline.fromOnlineSession((OnlineSession) session);
			if (!StringUtils.isEmpty(userOnline.getUserId())) {
				onlineSessionList.add(UserOnline.fromOnlineSession((OnlineSession) session));
			}
		}
		model.addAttribute("onlineSessionList",onlineSessionList);
		return displayModelAndView("list");
	}
	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(title = "在线用户",logType = LogType.SELECT)
	@RequiresMethodPermissions("list")
	private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
						  HttpServletResponse response) throws IOException {
		// 预处理
		Collection<Session> onlineSessionList=sessionDAO.getActiveSessions(true,null,null);
		Page<Session> onlineSessionPage=new PageImpl<Session>((List<Session>) onlineSessionList);
		String content = JSON.toJSONString(onlineSessionPage);
		StringUtils.printJson(response, content);
	}
	@RequestMapping("/forceLogout")
	@ResponseBody
	@Log(title = "用户强制退出")
	@RequiresMethodPermissions("force:logout")
	public Response forceLogout(@RequestParam(value = "ids") String[] ids) {
		for (String id : ids) {
			OnlineSession onlineSession = (OnlineSession) sessionDAO.readSession(id);
			if (onlineSession == null) {
				continue;
			}
			onlineSession.setStatus(OnlineSession.OnlineStatus.force_logout);
			sessionDAO.update(onlineSession);
		}
		return Response.ok("强制退出成功");
	}

}
