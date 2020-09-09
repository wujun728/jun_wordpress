package cn.jeeweb.bbs.modules.example.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.example.entity.Example;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.example.service
* @title: example服务接口
* @description: example服务接口
* @author: 王存见
* @date: 2018-09-04 16:46:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IExampleService extends ICommonService<Example> {
    /**
     *
     * @title: selectUserList
     * @description: 查找主题列表
     * @param wrapper
     * @return
     * @return: List<User>
     */
    Page<Example> selectExamplePage(Page<Example> page, Wrapper<Example> wrapper,String userId);
}