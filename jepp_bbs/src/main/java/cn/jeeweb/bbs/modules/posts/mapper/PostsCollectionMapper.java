package cn.jeeweb.bbs.modules.posts.mapper;

import cn.jeeweb.bbs.modules.posts.entity.PostsCollection;
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
 * @package cn.jeeweb.bbs.modules.posts.mapper
 * @title: 收藏数据库控制层接口
 * @description: 收藏数据库控制层接口
 * @author: 王存见
 * @date: 2018-09-03 09:48:55
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface PostsCollectionMapper extends BaseMapper<PostsCollection> {
 /**
  *
  * @title: selectUserList
  * @description: 查找收藏列表
  * @param wrapper
  * @return
  * @return: List<User>
  */
 List<PostsCollection> selectCollectionList(Pagination page, @Param("ew") Wrapper<PostsCollection> wrapper);

}