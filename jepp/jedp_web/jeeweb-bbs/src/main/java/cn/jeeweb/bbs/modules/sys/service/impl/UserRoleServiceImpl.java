package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.entity.UserRole;
import cn.jeeweb.bbs.modules.sys.mapper.UserRoleMapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.sys.service.IUserRoleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl extends CommonServiceImpl<UserRoleMapper,UserRole> implements IUserRoleService {

}
