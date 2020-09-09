package cn.jeeweb.bbs.modules.posts.service.impl;

import cn.jeeweb.bbs.common.constant.DataBaseConstant;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.mapper.PostsMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service.impl
* @title: 主题服务实现
* @description: 主题服务实现
* @author: 王存见
* @date: 2018-08-29 17:50:29
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("postsService")
public class PostsServiceImpl  extends CommonServiceImpl<PostsMapper,Posts> implements  IPostsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostsCommentService postsCommentService;
    @Override
    public Posts findPostsById(String postsId) {
        return baseMapper.findPostsById(postsId);
    }

    @Override
    public boolean insert(Posts entity) {
        // 扣除财富值
        User user = userService.selectById(entity.getUid());
        if (!entity.getColumn().equals("0")){
            entity.setExperience(0);
        }else{
            if (entity.getExperience()>0){
                if (user.getExperience()<entity.getExperience()){
                    throw new RuntimeException("财富值不足，请签到获取财富值，不能提问！");
                }
                user.setExperience(user.getExperience() - entity.getExperience());
                userService.insertOrUpdate(user);
            }
        }
        return super.insert(entity);
    }

    @Override
    public Page<Posts> selectPostsPage(Page<Posts> page, Wrapper<Posts> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectPostsList(page, wrapper));
        return page;
    }

    @Override
    public boolean deleteById(Serializable id) {
        EntityWrapper entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("pid",id);
        List<PostsComment> commentList = postsCommentService.selectList(entityWrapper);
        for (PostsComment comment: commentList) {
            postsCommentService.deleteById(comment.getId());
        }
        return super.deleteById(id);
    }

    @Override
    public void logicalDeleteById(Serializable id) {
        Posts posts =  selectById(id);
        posts.setDelFlag(DataBaseConstant.DEL_FLAG_DELETE);
        insertOrUpdate(posts);
    }

    @Override
    public Page<Posts> selectWeekTopPostsPage(Page<Posts> page, String column) {
        page.setRecords(baseMapper.selectWeekTopPostsList(page, column));
        return page;
    }
}