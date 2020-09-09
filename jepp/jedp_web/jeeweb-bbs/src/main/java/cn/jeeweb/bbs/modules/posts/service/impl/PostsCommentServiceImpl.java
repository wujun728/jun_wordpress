package cn.jeeweb.bbs.modules.posts.service.impl;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.entity.PostsCommentUser;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentPraiseService;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.mapper.PostsCommentMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service.impl
* @title: 评论服务实现
* @description: 评论服务实现
* @author: 王存见
* @date: 2018-08-29 17:51:13
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("postsCommentService")
public class PostsCommentServiceImpl extends CommonServiceImpl<PostsCommentMapper,PostsComment> implements IPostsCommentService {
    @Autowired
    private IPostsService postsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostsCommentPraiseService postsCommentPraiseService;
    @Override
    public Page<PostsComment> selectCommentPage(Page<PostsComment> page, Wrapper<PostsComment> wrapper, String userId) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectCommentList(page, wrapper,userId));
        return page;
    }

    @Override
    public boolean deleteById(Serializable id) {
        EntityWrapper entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("tid",id);
        postsCommentPraiseService.delete(entityWrapper);
        return super.deleteById(id);
    }

    @Override
    public Page<PostsCommentUser> selectWeekCommentUserPage(Page<PostsCommentUser> page) {
        page.setRecords( baseMapper.selectWeekCommentUserList(page));
        return page;
    }

    @Override
    public void accept(String id) {
        PostsComment comment = selectById(id);
        // 更新为已结
        Posts posts = postsService.selectById(comment.getPid());
        posts.setSolved(1);
        postsService.insertOrUpdate(posts);
        // 更新被采纳
        comment.setAccept("1");
        insertOrUpdate(comment);
        // 被采纳的用户加入财富值
        if (posts.getExperience()>0) { // 发送消息
            String commentUid = comment.getUid();
            User user = userService.selectById(commentUid);
            user.setExperience(user.getExperience()+posts.getExperience());
            userService.insertOrUpdate(user);
        }
    }
}