package cn.jeeweb.web.utils;

import cn.jeeweb.common.utils.MessageUtils;
import cn.jeeweb.web.common.response.OAuthCloudResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * All rights Reserved, Designed By www.gzst.gov.cn
 *
 * @version V1.0
 * @package cn.gov.gzst.api.oauth.common.utils
 * @title:
 * @description: 错误返回工具类
 * @author: 王存见
 * @date: 2018/1/8 15:25
 * @copyright: 2017 www.gzst.gov.cn Inc. All rights reserved.
 */
public class ResponseUtils {
    public static ResponseEntity getErrResponse(int errorResponse, int errcode) throws OAuthSystemException {
        OAuthResponse response =
                OAuthCloudResponse.errorCloudResponse(errorResponse)
                        .setErrcode(errcode)
                        .setErrmsg(MessageUtils.getMessage(errcode+""))
                        .buildJSONMessage();
        return new ResponseEntity(response.getBody(), HttpStatus.valueOf(200));
    }

    public static ResponseEntity getErrResponse(int errorResponse, int errcode, String errmsg) throws OAuthSystemException {
        OAuthResponse response =
                OAuthCloudResponse.errorCloudResponse(errorResponse)
                        .setErrcode(errcode)
                        .setErrmsg(errmsg)
                        .buildJSONMessage();
        return new ResponseEntity(response.getBody(), HttpStatus.valueOf(200));
    }
}
