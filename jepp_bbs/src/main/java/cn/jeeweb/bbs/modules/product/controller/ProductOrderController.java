package cn.jeeweb.bbs.modules.product.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.product.service.IProductOrderService;
import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.product.controller
 * @title: 产品订单控制器
 * @description: 产品订单控制器
 * @author: 王存见
 * @date: 2018-09-19 10:42:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/product/order")
@RequiresPathPermission("product:order")
@ViewPrefix("modules/product")
public class ProductOrderController extends BaseBeanController<ProductOrder> {

    @Autowired
    private IProductOrderService productOrderService;


    @GetMapping
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return displayModelAndView("order_list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<ProductOrder> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        entityWrapper.setTableAlias("po");
        entityWrapper.orderBy("createDate",false);
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<ProductOrder> pagejson = new PageResponse<>(productOrderService.selectProductOrderPage(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @PostMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        productOrderService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        productOrderService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }


}