package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.mapper.OrganizationMapper;
import cn.jeeweb.bbs.modules.sys.service.IOrganizationService;
import cn.jeeweb.common.mybatis.mvc.service.impl.TreeCommonServiceImpl;
import cn.jeeweb.bbs.modules.sys.entity.Organization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("organizationService")
public class OrganizationServiceImpl extends TreeCommonServiceImpl<OrganizationMapper, Organization, String>
		implements IOrganizationService {

	@Override
	public List<Organization> findListByUserId(String userid) {
		return baseMapper.findListByUserId(userid);
	}

}
