package cn.jeeweb.bbs.modules.sign.service;

import cn.jeeweb.bbs.modules.sign.entity.SignIn;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.sign.entity.SignInStatus;
import com.baomidou.mybatisplus.plugins.Page;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.sign.service
* @title: 登陆状态服务接口
* @description: 登陆状态状态服务接口
* @author: 王存见
* @date: 2018-09-05 18:45:25
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface ISignInStatusService extends ICommonService<SignInStatus> {

     SignInStatus selectLastSignInByUid(String uid);

     Page<SignInStatus> selectLatestSignPage(Page<SignInStatus> page);
}