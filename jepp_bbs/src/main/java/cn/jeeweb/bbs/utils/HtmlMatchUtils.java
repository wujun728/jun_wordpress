package cn.jeeweb.bbs.utils;

import cn.jeeweb.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.utils
 * @title:
 * @description: 前端编辑器内容处理
 * @author: 王存见
 * @date: 2018/9/3 16:38
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class HtmlMatchUtils {

    /**
     * 查找At的用户
     * @param content
     * @return
     */
    public static List<String> findMatchAtRealName(String content){
        List<String> realNameList = new ArrayList<>();
        String regex = "@(\\S+)(\\s+?|$)";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(content);
        while (m.find()) {
            realNameList.add(m.group(0).replace("@",""));
        }
        return realNameList;
    }
}
