package cn.jeeweb.bbs.modules.posts.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentPraiseService;
import cn.jeeweb.bbs.modules.posts.entity.PostsCommentPraise;
import cn.jeeweb.bbs.modules.posts.mapper.PostsCommentPraiseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service.impl
* @title: 点赞服务实现
* @description: 点赞服务实现
* @author: 王存见
* @date: 2018-08-29 17:51:39
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("postsCommentPraiseService")
public class PostsCommentPraiseServiceImpl extends CommonServiceImpl<PostsCommentPraiseMapper,PostsCommentPraise> implements IPostsCommentPraiseService {

}