package cn.jeeweb.bbs.utils;

import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.entity.PostsCommentUser;
import cn.jeeweb.bbs.modules.posts.service.IPostsColumnService;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.*;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import java.util.List;

public class PostsUtils {

	protected final static String POST_CACHE_NAME = "postsCache";
	public static final String POSTS_CACHE_COLUMN_ALIAS_ = "postsCacheColumnAlias_";
	public static final String POSTS_CACHE_COLUMN_CODE_ = "postsCacheColumnCode_";
	public static final String CACHE_POSTS_COLUMN_LIST= "postsColumnList";

	/**
	 * 别名获取栏目
	 *
	 * @param alias
	 * @return
	 */
	public static PostsColumn getPostsColumnByAlias(String alias) {
		PostsColumn postsColumn = (PostsColumn) CacheUtils.get(POST_CACHE_NAME, POSTS_CACHE_COLUMN_ALIAS_ + alias);
		if ("all".equals(alias)){
			postsColumn = new PostsColumn();
			postsColumn.setName("全部");
			postsColumn.setAlias(alias);
			return postsColumn;
		}
		if (postsColumn == null) {
			EntityWrapper<PostsColumn> entityWrapper = new EntityWrapper<>();
			entityWrapper.eq("alias",alias);
			postsColumn = SpringContextHolder.getBean(IPostsColumnService.class).selectOne(entityWrapper);
			if (postsColumn == null) {
				return null;
			}
			CacheUtils.put(POST_CACHE_NAME, POSTS_CACHE_COLUMN_ALIAS_ + alias, postsColumn);
		}
		return postsColumn;
	}

	/**
	 * 别名获取栏目
	 *
	 * @param code
	 * @return
	 */
	public static PostsColumn getPostsColumnByCode(String code) {
		PostsColumn postsColumn = (PostsColumn) CacheUtils.get(POST_CACHE_NAME, POSTS_CACHE_COLUMN_CODE_ + code);
		if (postsColumn == null) {
			EntityWrapper<PostsColumn> entityWrapper = new EntityWrapper<>();
			entityWrapper.eq("code",code);
			postsColumn = SpringContextHolder.getBean(IPostsColumnService.class).selectOne(entityWrapper);
			if (postsColumn == null) {
				return null;
			}
			CacheUtils.put(POST_CACHE_NAME, POSTS_CACHE_COLUMN_CODE_ + code, postsColumn);
		}
		return postsColumn;
	}

	/**
	 * 主题栏目列表
	 *
	 * @return
	 */
	public static List<PostsColumn> getPostsColumnList() {
		List<PostsColumn> postsColumnList = (List<PostsColumn>)  CacheUtils.get(POST_CACHE_NAME, CACHE_POSTS_COLUMN_LIST);
		if (postsColumnList == null) {
			EntityWrapper<PostsColumn> entityWrapper = new EntityWrapper<>();
			entityWrapper.orderBy("sort");
			postsColumnList = SpringContextHolder.getBean(IPostsColumnService.class).selectList(entityWrapper);
			CacheUtils.put(POST_CACHE_NAME, CACHE_POSTS_COLUMN_LIST,postsColumnList);
		}
		return postsColumnList;
	}

	/**
	 * 回贴周榜
	 *
	 * @return
	 */
	public static Page selectWeekCommentUserPage(int size) {
		Page<PostsCommentUser> pageBean = new com.baomidou.mybatisplus.plugins.Page<PostsCommentUser>(
				1, size);
		IPostsCommentService postsCommentService =  SpringContextHolder.getBean(IPostsCommentService.class);
		pageBean = postsCommentService.selectWeekCommentUserPage(pageBean);
		return pageBean;
	}


	/**
	 * 本周热议
	 *
	 * @return
	 */
	public static Page selectWeekTopPostsPage(String columnAlias,int size) {
		String column = "";
		if (!StringUtils.isEmpty(columnAlias)){
			PostsColumn postsColumn = getPostsColumnByAlias(columnAlias);
			if(postsColumn!=null) {
				column = postsColumn.getCode();
			}
		}
		Page<Posts> pageBean = new com.baomidou.mybatisplus.plugins.Page<Posts>(
				1, size);
		IPostsService postsService =  SpringContextHolder.getBean(IPostsService.class);
		pageBean = postsService.selectWeekTopPostsPage(pageBean,column);
		return pageBean;
	}
}
