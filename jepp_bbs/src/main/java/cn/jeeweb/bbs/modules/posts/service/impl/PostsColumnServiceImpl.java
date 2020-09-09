package cn.jeeweb.bbs.modules.posts.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.posts.service.IPostsColumnService;
import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import cn.jeeweb.bbs.modules.posts.mapper.PostsColumnMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service.impl
* @title: 帖子栏目服务实现
* @description: 帖子栏目服务实现
* @author: 王存见
* @date: 2018-08-30 22:50:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("postscolumnService")
public class PostsColumnServiceImpl  extends CommonServiceImpl<PostsColumnMapper,PostsColumn> implements  IPostsColumnService {

}