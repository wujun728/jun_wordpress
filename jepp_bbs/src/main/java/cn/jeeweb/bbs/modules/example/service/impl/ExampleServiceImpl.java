package cn.jeeweb.bbs.modules.example.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.example.service.IExampleService;
import cn.jeeweb.bbs.modules.example.entity.Example;
import cn.jeeweb.bbs.modules.example.mapper.ExampleMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.example.service.impl
* @title: example服务实现
* @description: example服务实现
* @author: 王存见
* @date: 2018-09-04 16:46:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("exampleService")
public class ExampleServiceImpl  extends CommonServiceImpl<ExampleMapper,Example> implements  IExampleService {
    @Override
    public Page<Example> selectExamplePage(Page<Example> page, Wrapper<Example> wrapper,String userId) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectExampleList(page, wrapper,userId));
        return page;
    }
}