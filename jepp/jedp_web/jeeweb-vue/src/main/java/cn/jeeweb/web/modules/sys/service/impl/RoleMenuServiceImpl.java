package cn.jeeweb.web.modules.sys.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.modules.sys.entity.Menu;
import cn.jeeweb.web.modules.sys.entity.RoleMenu;
import cn.jeeweb.web.modules.sys.mapper.RoleMenuMapper;
import cn.jeeweb.web.modules.sys.service.IMenuService;
import cn.jeeweb.web.modules.sys.service.IRoleMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("roleMenuService")
public class RoleMenuServiceImpl extends CommonServiceImpl<RoleMenuMapper,RoleMenu> implements IRoleMenuService {

    @Autowired
    private IMenuService menuService;

    @Override
    public void setMenu(String roleId, String menuIds) {
        if (!StringUtils.isEmpty(menuIds)) {
            // 删除菜单关联
            delete(new EntityWrapper<RoleMenu>(RoleMenu.class).eq("roleId", roleId));
            String[] selectMenus = menuIds.split(",");
            List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
            for (String menuId : selectMenus) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuList.add(roleMenu);
            }
            insertOrUpdateBatch(roleMenuList);
        }
    }

    @Override
    public void insert(String roleId, String menuId) {
        EntityWrapper<RoleMenu> entityWrapper = new EntityWrapper<>(RoleMenu.class);
        entityWrapper.eq("menuId",menuId);
        entityWrapper.eq("roleId",roleId);
        int count = selectCount(entityWrapper);
        if(count == 0){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            insert(roleMenu);
        }
    }
}
