package cn.jeeweb.bbs.modules.product.task;

import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import cn.jeeweb.bbs.modules.product.service.IProductOrderService;
import cn.jeeweb.bbs.modules.product.service.IProductService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.product.task
 * @title:
 * @description: 后台定时任务
 * @author: 王存见
 * @date: 2018/9/19 15:09
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Component("payTask")
public class PayTask {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductOrderService productOrderService;

    @Autowired
    private PayService payService;

    /**
     *  这里处理订单已经付款，但不回掉，程序查询订单
     */
    public void run(){

        /**
         * 查找一分钟内未支付的产品订单
         */
        List<String> interval1ProductOrderList = productOrderService.selectIntervalProductOrderList(1);
        for (String productOrderId: interval1ProductOrderList) {
            productOrderService.checkOrder(productOrderId);
            sleep();
        }

        /**
         * 查找十分钟内未支付的产品订单
         */
        List<String> interval10ProductOrderList = productOrderService.selectIntervalProductOrderList(10);
        for (String productOrderId: interval10ProductOrderList) {
            productOrderService.closeOrder(productOrderId);
            sleep();
        }

    }
    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
