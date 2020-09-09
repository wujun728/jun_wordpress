package cn.jeeweb.bbs.modules.posts.entity;

import cn.jeeweb.bbs.modules.sys.entity.User;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.posts.entity
 * @title:
 * @description: 评论用户
 * @author: 王存见
 * @date: 2018/9/3 11:22
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class PostsCommentUser extends User {
   private Integer commentCount;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
