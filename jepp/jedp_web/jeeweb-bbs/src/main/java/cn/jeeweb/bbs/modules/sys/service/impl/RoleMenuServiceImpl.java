package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.mapper.RoleMenuMapper;
import cn.jeeweb.bbs.modules.sys.service.IRoleMenuService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.sys.entity.RoleMenu;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("roleMenuService")
public class RoleMenuServiceImpl extends CommonServiceImpl<RoleMenuMapper,RoleMenu> implements IRoleMenuService {

}
