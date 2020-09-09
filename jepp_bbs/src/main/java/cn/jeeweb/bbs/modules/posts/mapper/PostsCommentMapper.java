package cn.jeeweb.bbs.modules.posts.mapper;

import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.entity.PostsCommentUser;
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
 * @title: 评论数据库控制层接口
 * @description: 评论数据库控制层接口
 * @author: 王存见
 * @date: 2018-08-29 17:51:13
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface PostsCommentMapper extends BaseMapper<PostsComment> {
 /**
  *
  * @title: selectUserList
  * @description: 查找主题评论列表
  * @param wrapper
  * @return
  * @return: List<User>
  */
 List<PostsComment> selectCommentList(Pagination page, @Param("ew") Wrapper<PostsComment> wrapper, @Param("userId") String userId);

 /**
  *
  * @title: selectUserList
  * @description: 查找主题评论周榜
  * @return
  * @return: List<User>
  */
 List<PostsCommentUser> selectWeekCommentUserList(Pagination page);

}