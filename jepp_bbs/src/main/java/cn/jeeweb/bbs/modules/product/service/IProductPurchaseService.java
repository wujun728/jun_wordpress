package cn.jeeweb.bbs.modules.product.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.product.entity.ProductPurchase;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.product.service
* @title: 购买产品服务接口
* @description: 购买产品服务接口
* @author: 王存见
* @date: 2018-09-25 09:51:47
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IProductPurchaseService extends ICommonService<ProductPurchase> {
    /**
     * 分页查询
     * @param page
     * @param wrapper
     * @return
     */
    Page<ProductPurchase> selectProductPurchasePage(Page<ProductPurchase> page, Wrapper<ProductPurchase> wrapper);

}