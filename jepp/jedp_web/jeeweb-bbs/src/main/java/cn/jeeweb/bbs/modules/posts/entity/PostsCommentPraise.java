package cn.jeeweb.bbs.modules.posts.entity;

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
 * @title: 点赞实体
 * @description: 点赞实体
 * @author: 王存见
 * @date: 2018-08-29 17:51:39
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("posts_comment_praise")
@SuppressWarnings("serial")
public class PostsCommentPraise extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**对象ID*/
    @TableField(value = "tid")
	private String tid;
    /**创建时间*/
    @TableField(value = "praise_time")
	private Date praiseTime;
    /**praise_uid*/
    @TableField(value = "praise_uid")
	private String praiseUid;
	
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
	 * 获取  praiseTime
	 *@return Date  创建时间
	 */
	public Date getPraiseTime(){
		return this.praiseTime;
	}

	/**
	 * 设置  praiseTime
	 *@param praiseTime  创建时间
	 */
	public void setPraiseTime(Date praiseTime){
		this.praiseTime = praiseTime;
	}
	/**
	 * 获取  praiseUid
	 *@return String  praise_uid
	 */
	public String getPraiseUid(){
		return this.praiseUid;
	}

	/**
	 * 设置  praiseUid
	 *@param praiseUid  praise_uid
	 */
	public void setPraiseUid(String praiseUid){
		this.praiseUid = praiseUid;
	}
	
}