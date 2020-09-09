package cn.jeeweb.bbs.config;

import cn.jeeweb.bbs.config.autoconfigure.PayConfigProperties;
import cn.jeeweb.bbs.config.autoconfigure.ShiroConfigProperties;
import cn.jeeweb.common.utils.FileUtil;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.http.HttpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.config
 * @title:
 * @description: 支付配置
 * @author: 王存见
 * @date: 2018/9/19 10:17
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration
public class PayConfig {

    @Autowired
    private Environment env;

  /*  @Bean
    @ConfigurationProperties("pay.alipay")
    public AliPayConfigStorage aliPayConfigStorage()
    {
        return new AliPayConfigStorage();
    }

    @Bean
    @ConfigurationProperties("pay")
    public PayConfigProperties payConfigProperties()
    {
        return new PayConfigProperties();
    }
   */
    @Bean
    public PayConfigProperties payConfigProperties()
    {
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setAppId(env.getProperty("pay.alipay.app-id"));
        aliPayConfigStorage.setPid(env.getProperty("pay.alipay.pid"));
        aliPayConfigStorage.setKeyPublic(env.getProperty("pay.alipay.key-public"));
        aliPayConfigStorage.setKeyPrivate(env.getProperty("pay.alipay.key-private"));
        aliPayConfigStorage.setNotifyUrl(env.getProperty("pay.alipay.notify-url"));
        aliPayConfigStorage.setReturnUrl(env.getProperty("pay.alipay.return-url"));
        aliPayConfigStorage.setSignType(env.getProperty("pay.alipay.sign-type"));
        aliPayConfigStorage.setSeller(env.getProperty("pay.alipay.seller"));
        aliPayConfigStorage.setInputCharset(env.getProperty("pay.alipay.input-charset"));
        aliPayConfigStorage.setTest(Boolean.valueOf(env.getProperty("pay.alipay.test")));
        PayConfigProperties payConfigProperties= new PayConfigProperties();
        payConfigProperties.setAlipay(aliPayConfigStorage);
        return payConfigProperties;
    }

    /**
     * 目前只做阿里云支付
     *
     * @return
     */
    @Bean
    public PayService payService(PayConfigProperties payConfigProperties){
        //请求连接池配置
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        //最大连接数
        httpConfigStorage.setMaxTotal(3);
        //默认的每个路由的最大连接数
        httpConfigStorage.setDefaultMaxPerRoute(3);
        AliPayConfigStorage aliPayConfigStorage = payConfigProperties.getAlipay();
        PayService  payService = new AliPayService(aliPayConfigStorage,httpConfigStorage);
        return payService;
    }

}
