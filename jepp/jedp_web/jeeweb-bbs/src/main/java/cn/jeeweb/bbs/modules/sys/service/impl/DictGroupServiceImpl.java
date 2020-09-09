package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.entity.DictGroup;
import cn.jeeweb.bbs.modules.sys.mapper.DictGroupMapper;
import cn.jeeweb.bbs.modules.sys.service.IDictGroupService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dictGroupService")
public class DictGroupServiceImpl extends CommonServiceImpl<DictGroupMapper,DictGroup> implements IDictGroupService {
}
