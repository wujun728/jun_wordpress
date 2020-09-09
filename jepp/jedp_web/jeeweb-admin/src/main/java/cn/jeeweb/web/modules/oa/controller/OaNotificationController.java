package cn.jeeweb.web.modules.oa.controller;


import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jeeweb.common.mybatis.mvc.controller.BaseCRUDController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.modules.oa.entity.OaNotification;

/**   
 * @Title: 通知公告
 * @Description: 通知公告
 * @author jeeweb
 * @date 2017-06-10 17:15:17
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("${jeeweb.admin.url.prefix}/oa/oanotification")
@ViewPrefix("modules/oa/oanotification")
@RequiresPathPermission("oa:oanotification")
public class OaNotificationController extends BaseCRUDController<OaNotification, String> {

}
