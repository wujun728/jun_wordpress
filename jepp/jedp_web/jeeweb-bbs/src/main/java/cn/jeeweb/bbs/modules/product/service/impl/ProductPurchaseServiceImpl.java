package cn.jeeweb.bbs.modules.product.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.product.service.IProductPurchaseService;
import cn.jeeweb.bbs.modules.product.entity.ProductPurchase;
import cn.jeeweb.bbs.modules.product.mapper.ProductPurchaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.product.service.impl
* @title: 购买产品服务实现
* @description: 购买产品服务实现
* @author: 王存见
* @date: 2018-09-25 09:51:47
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("productPurchaseService")
public class ProductPurchaseServiceImpl  extends CommonServiceImpl<ProductPurchaseMapper,ProductPurchase> implements  IProductPurchaseService {
    @Override
    public Page<ProductPurchase> selectProductPurchasePage(Page<ProductPurchase> page, Wrapper<ProductPurchase> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectProductPurchaseList(page, wrapper));
        return page;
    }
}