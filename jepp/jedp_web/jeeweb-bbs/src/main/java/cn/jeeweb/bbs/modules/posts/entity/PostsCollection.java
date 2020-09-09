package cn.jeeweb.bbs.modules.posts.entity;

import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.posts.entity
 * @title: 收藏实体
 * @description: 收藏实体
 * @author: 王存见
 * @date: 2018-09-03 09:48:55
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("posts_collection")
@SuppressWarnings("serial")
public class PostsCollection extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**对象ID*/
    @TableField(value = "tid")
	private String tid;
    /**创建时间*/
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "collection_time")
	private Date collectionTime;
    /**collection_uid*/
    @TableField(value = "collection_uid")
	private String collectionUid;
	@TableField(exist =false)
    private Posts posts;
	@TableField(exist =false)
    private User user;
	
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
	 * 获取  tid
	 *@return String  对象ID
	 */
	public String getTid(){
		return this.tid;
	}

	/**
	 * 设置  tid
	 *@param tid  对象ID
	 */
	public void setTid(String tid){
		this.tid = tid;
	}
	/**
	 * 获取  collectionTime
	 *@return Date  创建时间
	 */
	public Date getCollectionTime(){
		return this.collectionTime;
	}

	/**
	 * 设置  collectionTime
	 *@param collectionTime  创建时间
	 */
	public void setCollectionTime(Date collectionTime){
		this.collectionTime = collectionTime;
	}
	/**
	 * 获取  collectionUid
	 *@return String  collection_uid
	 */
	public String getCollectionUid(){
		return this.collectionUid;
	}

	/**
	 * 设置  collectionUid
	 *@param collectionUid  collection_uid
	 */
	public void setCollectionUid(String collectionUid){
		this.collectionUid = collectionUid;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}