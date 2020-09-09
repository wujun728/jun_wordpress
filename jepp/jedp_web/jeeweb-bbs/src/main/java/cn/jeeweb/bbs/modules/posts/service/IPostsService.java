package cn.jeeweb.bbs.modules.posts.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.bbs.service
* @title: 主题服务接口
* @description: 主题服务接口
* @author: 王存见
* @date: 2018-08-29 17:50:29
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IPostsService extends ICommonService<Posts> {

    Posts findPostsById(String postsId);
    /**
     *
     * @title: selectUserList
     * @description: 查找主题列表
     * @param wrapper
     * @return
     * @return: List<User>
     */
    Page<Posts> selectPostsPage(Page<Posts> page, Wrapper<Posts> wrapper);

    /**
     *
     * @title: selectUserList
     * @description: 查找主题列表
     * @param column
     * @return
     * @return: List<User>
     */
    Page<Posts> selectWeekTopPostsPage(Page<Posts> page, String column);

    void logicalDeleteById(Serializable id);
}