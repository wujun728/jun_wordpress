package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.entity.UserOrganization;
import cn.jeeweb.bbs.modules.sys.mapper.UserOrganizationMapper;
import cn.jeeweb.bbs.modules.sys.service.IUserOrganizationService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("userOrganizationService")
public class UserOrganizationServiceImpl extends CommonServiceImpl<UserOrganizationMapper, UserOrganization>
		implements IUserOrganizationService {

}
