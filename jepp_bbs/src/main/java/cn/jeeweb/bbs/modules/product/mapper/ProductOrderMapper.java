package cn.jeeweb.bbs.modules.product.mapper;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.product.entity.ProductOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.product.mapper
 * @title: 产品订单数据库控制层接口
 * @description: 产品订单数据库控制层接口
 * @author: 王存见
 * @date: 2018-09-19 10:42:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {
   /**
    *
    * @title: selectProductOrderList
    * @description: 查找主题列表
    * @param wrapper
    * @return
    * @return: List<User>
    */
   List<ProductOrder> selectProductOrderList(Pagination page, @Param("ew") Wrapper<ProductOrder> wrapper);

   /**
    * 查找时间 interval 的数据
    * @param interval
    * @return
    */
   List<String> selectIntervalProductOrderList(@Param("interval") Integer interval);
}