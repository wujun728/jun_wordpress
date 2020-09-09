package cn.jeeweb.bbs.modules.posts.mapper;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
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
 * @package cn.jeeweb.bbs.modules.bbs.mapper
 * @title: 主题数据库控制层接口
 * @description: 主题数据库控制层接口
 * @author: 王存见
 * @date: 2018-08-29 17:50:29
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

  /**
   *
   * @title: findPostsById
   * @description: 通过ID查找
   * @param postsId
   * @return
   * @return: Posts
   */
   Posts findPostsById(String postsId);

  /**
   *
   * @title: selectUserList
   * @description: 查找主题列表
   * @param wrapper
   * @return
   * @return: List<User>
   */
  List<Posts> selectPostsList(Pagination page, @Param("ew") Wrapper<Posts> wrapper);

    /**
     *
     * @title: selectUserList
     * @description: 查找本周热议主题列表
     * @param column
     * @return
     * @return: List<User>
     */
    List<Posts> selectWeekTopPostsList(Pagination page, @Param("column") String column);
}