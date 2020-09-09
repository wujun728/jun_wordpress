package cn.jeeweb.bbs.modules.example.entity;

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
 * @package cn.jeeweb.bbs.modules.example.entity
 * @title: 点赞实体
 * @description: 点赞实体
 * @author: 王存见
 * @date: 2018-09-04 16:46:49
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("example_praise")
@SuppressWarnings("serial")
public class Praise extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**对象ID*/
    @TableField(value = "example_id")
	private String exampleId;
    /**创建时间*/
    @TableField(value = "praise_time")
	private Date praiseTime;
    /**praise_uid*/
    @TableField(value = "praise_uid")
	private String praiseUid;
	@TableField(exist = false)
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

	public String getExampleId() {
		return exampleId;
	}

	public void setExampleId(String exampleId) {
		this.exampleId = exampleId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}