package cn.jeeweb.bbs.modules.sign.service;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.sign.entity.SignInStatus;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.sign.entity.SignIn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.sign.service
* @title: 签到服务接口
* @description: 签到服务接口
* @author: 王存见
* @date: 2018-09-05 16:03:35
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface ISignInService extends ICommonService<SignIn> {
    /**
     * 判断昨天是否签到
     * @return
     */
    Integer countYesterdaySign(String userId);
    /**
     * 判断今天是否签到
     * @return
     */
    Integer countToDaySign(String userId);

    /**
     * 根据天数计算
     * @param day
     * @return
     */
    public Integer calculateExperience(Integer day);

    /**
     * 签到
     */
    SignInStatus signIn();

    Page<SignIn> selectToDaySignPage(Page<SignIn> page);

    Page<SignIn> selectLatestSignPage(Page<SignIn> page);


}