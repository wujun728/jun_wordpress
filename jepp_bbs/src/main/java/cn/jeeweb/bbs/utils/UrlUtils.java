package cn.jeeweb.bbs.utils;

import cn.jeeweb.common.utils.CacheUtils;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.utils
 * @title:
 * @description: url操作工具
 * @author: 王存见
 * @date: 2018/9/6 11:37
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class UrlUtils {
     private static String REQUEST_REFERER="request_referer";
    /**
     * 获取来源
     *
     * @return
     */
    public static String getRefererUrl(){
        // String referer= ServletUtils.getRequest().getHeader("referer");
        String referer= (String)CacheUtils.get(REQUEST_REFERER);
        if (StringUtils.isEmpty(referer)) {
            referer = "/posts/";
        }
        return referer;
    }

    public static String recordRefererUrl(){
        HttpServletRequest request = ServletUtils.getRequest();
        if (request.getMethod().equalsIgnoreCase("get")) {
            String referer = ServletUtils.getRequest().getHeader("referer");
            if (!StringUtils.isEmpty(referer)
                    &&!referer.contains("/user/register")
                    &&!referer.contains("/user/login")){
                CacheUtils.put(REQUEST_REFERER,referer);
            }
            return referer;
        }
        return "";
    }
}
