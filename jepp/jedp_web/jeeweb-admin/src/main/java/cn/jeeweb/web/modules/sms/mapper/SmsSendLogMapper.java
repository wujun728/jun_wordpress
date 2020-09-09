package cn.jeeweb.web.modules.sms.mapper;

import cn.jeeweb.web.modules.sms.entity.SmsSendLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.sms.mapper
* @title: 发送日志数据库控制层接口
* @description: 发送日志数据库控制层接口
* @author: 王存见
* @date: 2018-09-14 09:47:53
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Mapper
public interface SmsSendLogMapper extends BaseMapper<SmsSendLog> {

}