package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.bbs.common.constant.DataBaseConstant;
import cn.jeeweb.bbs.modules.front.request.QueryOrder;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.product.entity.Product;
import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import cn.jeeweb.bbs.modules.product.entity.ProductPurchase;
import cn.jeeweb.bbs.modules.product.service.IProductOrderService;
import cn.jeeweb.bbs.modules.product.service.IProductPurchaseService;
import cn.jeeweb.bbs.modules.product.service.IProductService;
import cn.jeeweb.bbs.utils.UserUtils;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.MethodType;
import com.egzosn.pay.common.bean.PayOrder;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.front.controller
 * @title:
 * @description: 产品管理
 * @author: 王存见
 * @date: 2018/9/18 16:08
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@RestController("FrontProductController")
@RequestMapping("product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductOrderService productOrderService;
    @Autowired
    private IProductPurchaseService productPurchaseService;
    @Autowired
    private PayService payService;

    @GetMapping(value = "")
    public ModelAndView index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("modules/front/product/index");
    }

    @PostMapping(value = "getProduct")
    public Response getProduct(@RequestParam(value = "version") String version,
                          @RequestParam(value = "authorizationPeriod") String authorizationPeriod) {
        EntityWrapper<Product> entityWrapper = new EntityWrapper<>(Product.class);
        entityWrapper.eq("version",version);
        entityWrapper.eq("authorizationPeriod",authorizationPeriod);
        Product product =  productService.selectOne(entityWrapper);
        Response response = Response.ok();
        response.put("product",product);
        return response;
    }

    @GetMapping(value = {"order/{id}","order"})
    public ModelAndView order(@PathVariable(value = "id",required = false) String productId, Model model, HttpServletRequest request, HttpServletResponse response) {
        Product product =  productService.selectById(productId);
        if (product == null){
            product = productService.selectOne(new EntityWrapper<>());
        }
        if (!StringUtils.isEmpty(product.getDiscountDescription())){
            product.setDiscountDescription(StringEscapeUtils.unescapeHtml4(product.getDiscountDescription()));
        }
        model.addAttribute("product",product);
        return new ModelAndView("modules/front/product/order");
    }

    @GetMapping(value = "buy")
    public String buy(@RequestParam(value = "productId") String productId,
                            @RequestParam(value = "payType") String payType,
                            Model model, HttpServletRequest request, HttpServletResponse response) {
        //目前支持支付宝扫码付款
        PayOrder order =  productOrderService.createOrder(productId);
        Map orderInfo = payService.orderInfo(order);
        return payService.buildRequest(orderInfo, MethodType.POST);
    }


    /**
     * 支付回调地址
     *
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "payBack")
    public Object payBack(Model model,HttpServletRequest request) throws IOException {
        //获取支付方返回的对应参数
        Map<String, Object> params = payService.getParameter2Map(request.getParameterMap(), request.getInputStream());
        //校验
        if (params!=null&&payService.verify(params)) {
            //这里处理业务逻辑
            //......业务逻辑处理块........
            String out_trade_no = (String) params.get("out_trade_no");
            productOrderService.paySuccess(out_trade_no);
            if (ServletUtils.isAjax()) {
                return payService.getPayOutMessage("success", "成功").toMessage();
            }else{
                model.addAttribute("tips","支付成功！");
                return new ModelAndView("modules/front/other/tips");
            }
        }
        if (ServletUtils.isAjax()) {
            return payService.getPayOutMessage("success", "成功").toMessage();
        }else{
            model.addAttribute("tips","支付失败！");
            return new ModelAndView("modules/front/other/tips");
        }
    }

    /**
     * 查询
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("query")
    public Map<String, Object> query(QueryOrder order) {
        Map<String,Object> params =payService.query("", order.getOutTradeNo());
        if (params!=null&&payService.verify(params)) {
            Map<String, Object> alipayTrade = (Map<String, Object>) params.get("alipay_trade_query_response");
            if (alipayTrade!=null && alipayTrade.containsKey("trade_status")
                    &&"TRADE_SUCCESS".equals(alipayTrade.get("trade_status"))){
                System.out.println("ok");
            }
        }
        return null;
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     */
    @PostMapping(value = "mine")
    private Response mine(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit) throws IOException {
        Response response = Response.ok();
        EntityWrapper<ProductPurchase> entityWrapper = new EntityWrapper<>(ProductPurchase.class);
        entityWrapper.setTableAlias("po");
        entityWrapper.orderBy("createDate",false);
        entityWrapper.eq("uid",UserUtils.getUser().getId());
        Page<ProductPurchase> productPurchasePageBean = new com.baomidou.mybatisplus.plugins.Page<>(
                page, limit);
        productPurchasePageBean = productPurchaseService.selectProductPurchasePage(productPurchasePageBean,entityWrapper);
        response.put("count",productPurchasePageBean.getTotal());
        response.putList("data",productPurchasePageBean.getRecords());
        return response;
    }

    @PostMapping(value = "{id}/download")
    public ModelAndView download(Model model,@PathVariable(value = "id") String id) {
        ProductPurchase productPurchase = productPurchaseService.selectById(id);
        model.addAttribute("tips","下载演示页码！");
        return new ModelAndView("modules/front/product/download");
    }

}
