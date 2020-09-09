package cn.jeeweb.bbs.modules.product.service;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import cn.jeeweb.common.query.data.Queryable;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.egzosn.pay.common.bean.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.product.service
* @title: 产品订单服务接口
* @description: 产品订单服务接口
* @author: 王存见
* @date: 2018-09-19 10:42:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IProductOrderService extends ICommonService<ProductOrder> {

    /**
     * 查找
     * @param interval
     * @return
     */
    List<String> selectIntervalProductOrderList(Integer interval);

    /**
     * 分页查询
     * @param page
     * @param wrapper
     * @return
     */
    Page<ProductOrder> selectProductOrderPage(Page<ProductOrder> page, Wrapper<ProductOrder> wrapper);

    /**
     * 分页查询
     * @param queryable
     * @param wrapper
     * @return
     */
    cn.jeeweb.common.query.data.Page<ProductOrder> selectProductOrderPage(Queryable queryable, Wrapper<ProductOrder> wrapper);
    /**
     * 创建订单
     * @param productId
     * @return
     */
    PayOrder createOrder(String productId);
    /**
     * 支付成功
     * @param orderId
     */
    void paySuccess(String orderId);
    /**
     * 检查订单是否支付
     * @param orderId
     * @return
     */
    void checkOrder(String orderId);
    /**
     * 关闭订单
     * @param orderId
     * @return
     */
    void closeOrder(String orderId);
}