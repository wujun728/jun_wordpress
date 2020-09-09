package cn.jeeweb.bbs.modules.product.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.product.service.IProductService;
import cn.jeeweb.bbs.modules.product.entity.Product;
import cn.jeeweb.bbs.modules.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.product.service.impl
* @title: 产品管理服务实现
* @description: 产品管理服务实现
* @author: 王存见
* @date: 2018-09-19 10:41:56
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("productService")
public class ProductServiceImpl  extends CommonServiceImpl<ProductMapper,Product> implements  IProductService {

}