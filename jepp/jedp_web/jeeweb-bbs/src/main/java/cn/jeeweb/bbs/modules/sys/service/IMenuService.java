package cn.jeeweb.bbs.modules.sys.service;

import java.util.List;

import cn.jeeweb.bbs.modules.sys.entity.Menu;
import cn.jeeweb.common.mybatis.mvc.service.ITreeCommonService;

/**
 * @Title:
 * @Description:
 * @author jwcg
 * @date 2014-12-20 21:33:32
 * @version V1.0
 *
 */
public interface IMenuService extends ITreeCommonService<Menu, String> {

	/**
	 * 通过用户ID查找菜单
	 * 
	 * @return
	 */
	List<Menu> findMenuByUserId(String userId);

	/**
	 * 通过角色查找菜单
	 * 
	 * @return
	 */
	List<Menu> findMenuByRoleId(String roleId);

	/**
	 * 通过用户ID查找菜单
	 *
	 * @return
	 */
	List<String> findPermissionByUserId(String userId);

	/**
	 * 通过用户ID查找菜单
	 *
	 * @return
	 */
	List<String> findPermissionByRoleId(String roleId);

	void generateButton(String menuId,
						String parentPermission,
						String[] permissions,
						String[] permissionTitles);
}
