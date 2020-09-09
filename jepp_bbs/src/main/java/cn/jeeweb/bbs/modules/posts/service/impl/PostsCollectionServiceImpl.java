package cn.jeeweb.bbs.modules.posts.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.posts.service.IPostsCollectionService;
import cn.jeeweb.bbs.modules.posts.entity.PostsCollection;
import cn.jeeweb.bbs.modules.posts.mapper.PostsCollectionMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.posts.service.impl
* @title: 收藏服务实现
* @description: 收藏服务实现
* @author: 王存见
* @date: 2018-09-03 09:48:55
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("postscollectionService")
public class PostsCollectionServiceImpl  extends CommonServiceImpl<PostsCollectionMapper,PostsCollection> implements  IPostsCollectionService {
    @Override
    public Page<PostsCollection> selectCollectionPage(Page<PostsCollection> page, Wrapper<PostsCollection> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectCollectionList(page, wrapper));
        return page;
    }
}