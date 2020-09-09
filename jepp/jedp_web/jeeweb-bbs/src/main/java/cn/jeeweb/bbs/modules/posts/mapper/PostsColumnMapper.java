package cn.jeeweb.bbs.modules.posts.mapper;

import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

 /**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.mapper
 * @title: 帖子栏目数据库控制层接口
 * @description: 帖子栏目数据库控制层接口
 * @author: 王存见
 * @date: 2018-08-30 22:50:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface PostsColumnMapper extends BaseMapper<PostsColumn> {
    
}