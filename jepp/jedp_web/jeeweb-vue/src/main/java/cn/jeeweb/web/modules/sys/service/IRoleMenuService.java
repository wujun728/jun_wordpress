package cn.jeeweb.web.modules.sys.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.modules.sys.entity.RoleMenu;

/**   
 * @Title: 
 * @Description: 
 * @author jeeweb
 * @date 2017-02-21 12:54:43
 * @version V1.0   
 *
 */
public interface IRoleMenuService extends ICommonService<RoleMenu>  {

    void insert(String roleId, String menuId);

    void setMenu(String roleId,String menuIds);
}

