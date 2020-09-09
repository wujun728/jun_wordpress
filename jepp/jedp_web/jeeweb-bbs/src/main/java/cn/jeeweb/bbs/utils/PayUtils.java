package cn.jeeweb.bbs.utils;

import cn.jeeweb.bbs.config.autoconfigure.PayConfigProperties;
import cn.jeeweb.common.utils.SpringContextHolder;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.utils
 * @title:
 * @description: 支付方法
 * @author: 王存见
 * @date: 2018/9/19 19:11
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class PayUtils {
    /**
     * 请求的时候总是出现超时，只好new一个
     * @return
     */
    public static AliPayService newPayService() {
        PayConfigProperties payConfigProperties = SpringContextHolder.getBean(PayConfigProperties.class);
        AliPayConfigStorage aliPayConfigStorage = payConfigProperties.getAlipay();
        AliPayService payService = new AliPayService(aliPayConfigStorage);
        return payService;
    }
}
