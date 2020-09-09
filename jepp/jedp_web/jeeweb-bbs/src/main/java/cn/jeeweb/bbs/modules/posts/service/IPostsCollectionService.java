package cn.jeeweb.bbs.modules.posts.service;

import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.posts.entity.PostsCollection;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.posts.service
* @title: 收藏服务接口
* @description: 收藏服务接口
* @author: 王存见
* @date: 2018-09-03 09:48:55
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IPostsCollectionService extends ICommonService<PostsCollection> {
    /**
     *
     * @title: selectCollectionPage
     * @description: 查找收藏列表
     * @param wrapper
     * @return
     * @return: List<User>
     */
    Page<PostsCollection> selectCollectionPage(Page<PostsCollection> page, Wrapper<PostsCollection> wrapper);

}