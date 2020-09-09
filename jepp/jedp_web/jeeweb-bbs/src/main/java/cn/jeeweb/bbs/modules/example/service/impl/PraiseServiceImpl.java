package cn.jeeweb.bbs.modules.example.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.example.service.IPraiseService;
import cn.jeeweb.bbs.modules.example.entity.Praise;
import cn.jeeweb.bbs.modules.example.mapper.PraiseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.example.service.impl
* @title: 点赞服务实现
* @description: 点赞服务实现
* @author: 王存见
* @date: 2018-09-04 16:46:49
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("praiseService")
public class PraiseServiceImpl  extends CommonServiceImpl<PraiseMapper,Praise> implements  IPraiseService {
    @Override
    public List<Praise> selectPraiseList(String exampleId) {
        return baseMapper.selectPraiseList(exampleId);
    }
}