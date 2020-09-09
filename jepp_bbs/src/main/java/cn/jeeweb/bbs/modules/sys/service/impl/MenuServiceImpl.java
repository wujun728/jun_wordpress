package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.entity.Menu;
import cn.jeeweb.bbs.modules.sys.mapper.MenuMapper;
import cn.jeeweb.bbs.modules.sys.service.IMenuService;
import cn.jeeweb.common.mybatis.mvc.service.impl.TreeCommonServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("menuService")
public class MenuServiceImpl extends TreeCommonServiceImpl<MenuMapper, Menu, String> implements IMenuService {

	@Override
	public List<Menu> findMenuByUserId(String userId) {
		return baseMapper.findMenuByUserId(userId);
	}

	@Override
	public List<Menu> findMenuByRoleId(String roleId) {
		return baseMapper.findMenuByRoleId(roleId);
	}

	@Override
	public List<String> findPermissionByUserId(String userId) {
		return baseMapper.findPermissionByUserId(userId);
	}

	@Override
	public List<String> findPermissionByRoleId(String roleId) {
		return baseMapper.findPermissionByRoleId(roleId);
	}
}
