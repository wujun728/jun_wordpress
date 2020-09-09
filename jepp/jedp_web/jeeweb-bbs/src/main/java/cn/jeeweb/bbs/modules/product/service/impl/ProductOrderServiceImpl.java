package cn.jeeweb.bbs.modules.product.service.impl;

import cn.jeeweb.bbs.config.autoconfigure.PayConfigProperties;
import cn.jeeweb.bbs.modules.front.constant.FrontConstant;
import cn.jeeweb.bbs.modules.product.entity.Product;
import cn.jeeweb.bbs.modules.product.entity.ProductPurchase;
import cn.jeeweb.bbs.modules.product.service.IProductPurchaseService;
import cn.jeeweb.bbs.modules.product.service.IProductService;
import cn.jeeweb.bbs.modules.sys.service.IMessageService;
import cn.jeeweb.bbs.utils.PayUtils;
import cn.jeeweb.bbs.utils.UserUtils;
import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.product.service.IProductOrderService;
import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import cn.jeeweb.bbs.modules.product.mapper.ProductOrderMapper;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.product.service.impl
* @title: 产品订单服务实现
* @description: 产品订单服务实现
* @author: 王存见
* @date: 2018-09-19 10:42:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("productorderService")
public class ProductOrderServiceImpl  extends CommonServiceImpl<ProductOrderMapper,ProductOrder> implements  IProductOrderService {

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductPurchaseService productPurchaseService;
    @Autowired
    private IMessageService messageService;

    @Override
    public List<String> selectIntervalProductOrderList(Integer interval) {
        return baseMapper.selectIntervalProductOrderList(interval);
    }

    @Override
    public Page<ProductOrder> selectProductOrderPage(Page<ProductOrder> page, Wrapper<ProductOrder> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectProductOrderList(page, wrapper));
        return page;
    }

    @Override
    public cn.jeeweb.common.query.data.Page<ProductOrder> selectProductOrderPage(Queryable queryable, Wrapper<ProductOrder> wrapper) {
        QueryToWrapper<ProductOrder> queryToWrapper = new QueryToWrapper<>();
        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<ProductOrder> page = new com.baomidou.mybatisplus.plugins.Page<>(
                pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectProductOrderList(page, wrapper));
        return new PageImpl<>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public PayOrder createOrder(String productId) {
        // 获取产品
        Product product = productService.selectById(productId);
        //生成一个订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(product.getId());
        productOrder.setName(product.getName());
        productOrder.setTotalAmount(product.getDiscountAmount());
        productOrder.setUid(UserUtils.getUser().getId());
        productOrder.setOrderStatus(ProductOrder.order_status_non_payment);
        insert(productOrder);
        String description = product.getDescription();
        if (StringUtils.isEmpty(description)){
            description = product.getName();
        }
        //支付宝扫码付款
        PayOrder order = new PayOrder(product.getName(),
                description,productOrder.getTotalAmount(),productOrder.getId(), AliTransactionType.DIRECT);
        return order;
    }

    @Override
    public void paySuccess(String orderId) {
        try {
            ProductOrder productOrder = selectById(orderId);
            productOrder.setOrderStatus(ProductOrder.order_status_account_success);
            productOrder.setPayDate(new Date());
            insertOrUpdate(productOrder);
            // 更新产品授权
            ProductPurchase productPurchase = createNewProductPurchase(productOrder);
            //发送通知信息
            Map<String, Object> datas = new HashMap<>();
            datas.put("name",productPurchase.getName());
            datas.put("id",productPurchase.getId());
            messageService.sendMessage(productOrder.getUid(),
                    FrontConstant.MESSAGE_COMMENT_PRODUCT_ORDDER_SUCCESS_TEMPLATE_CODE,datas);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private ProductPurchase createNewProductPurchase(ProductOrder productOrder){
        Product product = productService.selectById(productOrder.getProductId());
        ProductPurchase productPurchase = new ProductPurchase();
        EntityWrapper productPurchaseEntityWrapper = new EntityWrapper<>();
        productPurchaseEntityWrapper.eq("uid",productOrder.getUid());
        productPurchaseEntityWrapper.eq("product_id",productOrder.getProductId());
        productPurchase = productPurchaseService.selectOne(productPurchaseEntityWrapper);
        if (productPurchase==null){
            productPurchase = new ProductPurchase();
        }
        productPurchase.setName(productOrder.getName());
        String authorizationPeriod = product.getAuthorizationPeriod();
        if (authorizationPeriod.equals("2")) {
            productPurchase.setExpiryTime(null);
        }else{
            productPurchase.setExpiryTime(lastYear(productPurchase.getExpiryTime()));
        }
        productPurchase.setUpdateDate(new Date());
        productPurchase.setCreateDate(new Date());
        productPurchase.setUid(productOrder.getUid());
        productPurchase.setProductId(productOrder.getProductId());
        productPurchase.setPrice(productOrder.getTotalAmount());
        productPurchaseService.insert(productPurchase);
        return productPurchase;
    }


    /**
     * 下一年
     * @param date
     */
    private Date lastYear (Date date) {
        Calendar curr = Calendar.getInstance();
        if (date!=null) {
            curr.setTime(date);
        }
        curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);
        return curr.getTime();
    }

    @Override
    public void checkOrder(String orderId) {
        PayService payService = PayUtils.newPayService();
        Map<String,Object> params =payService.query("",orderId);
        if (params!=null&&payService.verify(params)) {
            Map<String, Object> alipayTrade = (Map<String, Object>) params.get("alipay_trade_query_response");
            if (alipayTrade!=null && alipayTrade.containsKey("trade_status")
                    &&"TRADE_SUCCESS".equals(alipayTrade.get("trade_status"))){
                paySuccess(orderId);
            }
        }
    }

    @Override
    public void closeOrder(String orderId) {
        ProductOrder productOrder = selectById(orderId);
        productOrder.setOrderStatus(ProductOrder.order_status_account_close);
        productOrder.setCloseTime(new Date());
        insertOrUpdate(productOrder);
        // 关闭
        try {
            PayUtils.newPayService().close("", orderId);
        }catch (Exception e) {
            // 不管是否成功都更改状态
        }
    }
}