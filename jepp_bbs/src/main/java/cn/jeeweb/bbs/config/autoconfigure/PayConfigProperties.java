package cn.jeeweb.bbs.config.autoconfigure;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.config.autoconfigure
 * @title:
 * @description: 支付配置，阿里配置
 * @author: 王存见
 * @date: 2018/9/19 10:16
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
//@ConditionalOnClass(AliPayConfigStorage.class)
//@ConfigurationProperties(prefix = "pay")
public class PayConfigProperties {

    private AliPayConfigStorage alipay;

    public AliPayConfigStorage getAlipay() {
        return alipay;
    }

    public void setAlipay(AliPayConfigStorage alipay) {
        this.alipay = alipay;
    }
}
