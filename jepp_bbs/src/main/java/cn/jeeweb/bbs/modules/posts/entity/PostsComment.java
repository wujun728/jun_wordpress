package cn.jeeweb.bbs.modules.posts.entity;

import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.entity
 * @title: 评论实体
 * @description: 评论实体
 * @author: 王存见
 * @date: 2018-08-29 17:51:13
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("posts_comment")
@SuppressWarnings("serial")
public class PostsComment extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**帖子ID*/
    @TableField(value = "pid")
	private String pid;
    /**content*/
    @TableField(value = "content")
	private String content;
    /**publish_time*/
    @TableField(value = "publish_time")
	private Date publishTime;
    /**uid*/
    @TableField(value = "uid")
	private String uid;
	@TableField(value = "accept")
    private String accept;
	@TableField(exist = false)
	private User user;
	@TableField(exist = false)
	private Posts posts;
	@TableField(exist = false)
	private Integer praiseCount;
	@TableField(exist = false)
	private Integer praise;

	public void setDefault(){
		/**是否采纳*/
		this.accept = "0";
	}
	/**
	 * 获取  id
	 *@return String  id
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * 设置  id
	 *@param id  id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取  pid
	 *@return String  帖子ID
	 */
	public String getPid(){
		return this.pid;
	}

	/**
	 * 设置  pid
	 *@param pid  帖子ID
	 */
	public void setPid(String pid){
		this.pid = pid;
	}
	/**
	 * 获取  content
	 *@return String  content
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * 设置  content
	 *@param content  content
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取  publishTime
	 *@return Date  publish_time
	 */
	public Date getPublishTime(){
		return this.publishTime;
	}

	/**
	 * 设置  publishTime
	 *@param publishTime  publish_time
	 */
	public void setPublishTime(Date publishTime){
		this.publishTime = publishTime;
	}
	/**
	 * 获取  uid
	 *@return String  uid
	 */
	public String getUid(){
		return this.uid;
	}

	/**
	 * 设置  uid
	 *@param uid  uid
	 */
	public void setUid(String uid){
		this.uid = uid;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getPraise() {
		if (praise == null){
			praise = 0;
		}
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}
}