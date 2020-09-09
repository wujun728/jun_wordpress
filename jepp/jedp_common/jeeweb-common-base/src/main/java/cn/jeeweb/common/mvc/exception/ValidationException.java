package cn.jeeweb.common.mvc.exception;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.common.mvc.exception
 * @title:
 * @description: 验证错误
 * @author: 王存见
 * @date: 2018/10/5 11:12
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class ValidationException extends RuntimeException {


    public ValidationException() {
        super();
    }


    public ValidationException(String message) {
        super(message);
    }
}
