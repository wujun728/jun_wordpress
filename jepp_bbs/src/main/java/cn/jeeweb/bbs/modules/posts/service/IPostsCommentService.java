package cn.jeeweb.bbs.modules.posts.service;

import cn.jeeweb.bbs.modules.posts.entity.PostsCommentUser;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service
* @title: 评论服务接口
* @description: 评论服务接口
* @author: 王存见
* @date: 2018-08-29 17:51:13
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IPostsCommentService extends ICommonService<PostsComment> {
    /**
     *
     * @title: selectUserList
     * @description: 查找主题评论列表
     * @param wrapper
     * @return
     * @return: List<User>
     */
    Page<PostsComment> selectCommentPage(Page<PostsComment> page, Wrapper<PostsComment> wrapper, String userId);

    /**
     *
     * @title: selectUserList
     * @description: 查找主题评论周榜单列表
     * @return
     * @return: List<User>
     */
    Page<PostsCommentUser> selectWeekCommentUserPage(Page<PostsCommentUser> page);

    /**
     * 采纳
     */
    void accept(String id);
}