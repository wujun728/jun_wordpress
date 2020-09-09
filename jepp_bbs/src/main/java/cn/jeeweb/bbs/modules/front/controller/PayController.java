package cn.jeeweb.bbs.modules.front.controller;


import cn.jeeweb.bbs.modules.front.request.QueryOrder;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayMessageRouter;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.*;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.http.UriVariables;
import com.egzosn.pay.common.util.sign.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.example.controller
 * @title: 支付控制器
 * @description: 支付控制器
 * @author: 王存见
 * @date: 2018-09-04 16:46:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@RestController
@RequestMapping("pay")
public class PayController {

    @Autowired
    private PayService payService = null;

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    public String toPay(BigDecimal price) {
        //及时收款
        PayOrder order = new PayOrder("订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""), AliTransactionType.DIRECT);
        //WAP
//        PayOrder order = new PayOrder("订单title", "摘要", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""), AliTransactionType.WAP);

        Map orderInfo = payService.orderInfo(order);
        return payService.buildRequest(orderInfo, MethodType.POST);
    }




    /**
     * 获取支付预订单信息
     *
     * @return 支付预订单信息
     */
    @RequestMapping("app")
    public Map<String, Object> app() {
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        PayOrder order = new PayOrder("订单title", "摘要", new BigDecimal(0.01), UUID.randomUUID().toString().replace("-", ""));
        //App支付
        order.setTransactionType(AliTransactionType.APP);
        data.put("orderInfo", UriVariables.getMapToParameters(payService.orderInfo(order)));
        return data;
    }

    /**
     * 获取二维码图像
     * 二维码支付
     * @param price       金额
     * @return 二维码图像
     */
    @RequestMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    public byte[] toWxQrPay( BigDecimal price) throws IOException {
        //获取对应的支付账户操作工具（可根据账户id）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(payService.genQrPay( new PayOrder("订单title", "摘要", null == price ? new BigDecimal(0.01) : price, System.currentTimeMillis()+"", AliTransactionType.SWEEPPAY)), "JPEG", baos);
        return baos.toByteArray();
    }


    /**
     * 刷卡付,pos主动扫码付款(条码付)
     * @param authCode        授权码，条码等
     * @param price       金额
     * @return 支付结果
     */
    @RequestMapping(value = "microPay")
    public Map<String, Object> microPay(BigDecimal price, String authCode) throws IOException {
        //获取对应的支付账户操作工具（可根据账户id）
        //条码付
        PayOrder order = new PayOrder("huodull order", "huodull order", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""), AliTransactionType.BAR_CODE);
           //声波付
//        PayOrder order = new PayOrder("huodull order", "huodull order", null == price ? new BigDecimal(0.01) : price, UUID.randomUUID().toString().replace("-", ""), AliTransactionType.WAVE_CODE);
        //设置授权码，条码等
        order.setAuthCode(authCode);
        //支付结果
        Map<String, Object> params = payService.microPay(order);
        //校验
        if (payService.verify(params)) {

            //支付校验通过后的处理
            //......业务逻辑处理块........


        }
        //这里开发者自行处理
        return params;
    }

    /**
     * 支付回调地址
     *
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "payBack.json")
    public String payBack(HttpServletRequest request) throws IOException {

        //获取支付方返回的对应参数
        Map<String, Object> params = payService.getParameter2Map(request.getParameterMap(), request.getInputStream());
        if (null == params) {
            return payService.getPayOutMessage("fail", "失败").toMessage();
        }
        //校验
        if (payService.verify(params)) {
            //这里处理业务逻辑
            //......业务逻辑处理块........
            //"total_amount" -> "0.01"
            params.get("out_trade_no");
            return payService.getPayOutMessage("success", "成功").toMessage();
        }

        return payService.getPayOutMessage("fail", "失败").toMessage();
    }


    /**
     * 查询
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("query")
    public Map<String, Object> query(QueryOrder order) {
        return payService.query("", order.getOutTradeNo());
    }


    /**
     * 交易关闭接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    @RequestMapping("close")
    public Map<String, Object> close(QueryOrder order) {
        return payService.close(order.getTradeNo(), order.getOutTradeNo());
    }

    /**
     * 申请退款接口
     *
     * @param order 订单的请求体
     * @return 返回支付方申请退款后的结果
     */
    @RequestMapping("refund")
    public Map<String, Object> refund(RefundOrder order) {
        return payService.refund(order);
    }

    /**
     * 查询退款
     *
     * @param order 订单的请求体
     * @return 返回支付方查询退款后的结果
     */
    @RequestMapping("refundquery")
    public Map<String, Object> refundquery(QueryOrder order) {
        return payService.refundquery(order.getTradeNo(), order.getOutTradeNo());
    }

    /**
     * 下载对账单
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    @RequestMapping("downloadbill")
    public Object downloadbill(QueryOrder order) {
        return payService.downloadbill(order.getBillDate(), order.getBillType());
    }


    /**
     * 通用查询接口，根据 AliTransactionType 类型进行实现,此接口不包括退款
     *
     * @param order 订单的请求体
     * @return 返回支付方对应接口的结果
     */
    @RequestMapping("secondaryInterface")
    public Map<String, Object> secondaryInterface(QueryOrder order) {
        TransactionType type = AliTransactionType.valueOf(order.getTransactionType());
        return payService.secondaryInterface(order.getTradeNoOrBillDate(), order.getOutTradeNoBillType(), type);
    }

    /**
     * 转账
     *
     * @param order 转账订单
     *
     * @return 对应的转账结果
     */
    @RequestMapping("transfer")
    public Map<String, Object> transfer(TransferOrder order) {
//        order.setOutNo("转账单号");
//        order.setPayeeAccount("收款方账户,支付宝登录号，支持邮箱和手机号格式");
//        order.setAmount(new BigDecimal(10));
//        order.setPayerName("付款方姓名, 非必填");
//        order.setPayeeName("收款方真实姓名, 非必填");
//        order.setRemark("转账备注, 非必填");
        return payService.transfer(order);
    }

    /**
     * 转账查询
     *
     * @param outNo   商户转账订单号
     * @param tradeNo 支付平台转账订单号
     *
     * @return 对应的转账订单
     */
    @RequestMapping("transferQuery")
    public Map<String, Object> transferQuery(String outNo, String tradeNo) {
        return payService.transferQuery(outNo, tradeNo);
    }
}
