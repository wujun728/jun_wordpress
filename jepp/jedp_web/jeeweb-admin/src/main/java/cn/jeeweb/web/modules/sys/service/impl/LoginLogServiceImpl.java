package cn.jeeweb.web.modules.sys.service.impl;

import cn.jeeweb.web.modules.sys.entity.LoginLog;
import cn.jeeweb.web.modules.sys.mapper.LoginLogMapper;
import cn.jeeweb.web.modules.sys.service.ILoginLogService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.sys.service.impl
* @title: 登陆日志服务实现
* @description: 登陆日志服务实现
* @author: sys
* @date: 2018-09-28 11:31:36
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("loginlogService")
public class LoginLogServiceImpl  extends CommonServiceImpl<LoginLogMapper,LoginLog> implements  ILoginLogService {

}