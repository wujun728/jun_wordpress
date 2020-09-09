package cn.jeeweb.bbs.utils;

import cn.jeeweb.bbs.modules.sms.service.ISmsSendService;
import cn.jeeweb.common.sms.client.ISmsClient;
import cn.jeeweb.common.sms.data.SmsResult;
import cn.jeeweb.common.sms.disruptor.SmsHelper;
import cn.jeeweb.common.utils.CacheUtils;
import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.utils
 * @title:
 * @description: 短信验证码
 * @author: 王存见
 * @date: 2018/9/11 17:21
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class SmsVercode {

    public static void generateCode(HttpServletRequest request,String phone,String template){
        String id = request.getRequestedSessionId();
        String vercode = StringUtils.randomNumber(5);
        Map<String,Object> datas = new HashMap<>();
        datas.put("code",vercode);
        ISmsSendService smsSendService = SpringContextHolder.getBean(ISmsSendService.class);
        CacheUtils.put("SmsVercode_"+phone+id, vercode);
        smsSendService.send(phone,template,datas);
    }

    public static Boolean validateCode(HttpServletRequest request,String phone,String code){
        String id = request.getRequestedSessionId();
        String vercode = (String) CacheUtils.get("SmsVercode_"+phone+id);
        if (code.equals(vercode)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
