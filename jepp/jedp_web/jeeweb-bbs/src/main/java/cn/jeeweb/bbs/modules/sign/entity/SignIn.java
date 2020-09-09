package cn.jeeweb.bbs.modules.sign.entity;

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
 * @package cn.jeeweb.bbs.sign.entity
 * @title: 签到实体
 * @description: 签到实体
 * @author: 王存见
 * @date: 2018-09-05 16:03:35
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("sign_in")
@SuppressWarnings("serial")
public class SignIn extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**签到的用户*/
    @TableField(value = "sign_uid")
	private String signUid;
    /**签到的时间*/
    @TableField(value = "sign_time")
	private Date signTime;
    /**获得签到积分*/
    @TableField(value = "experience")
	private Integer experience;
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
	/**
	 * 获取  signUid
	 *@return String  签到的用户
	 */
	public String getSignUid(){
		return this.signUid;
	}

	/**
	 * 设置  signUid
	 *@param signUid  签到的用户
	 */
	public void setSignUid(String signUid){
		this.signUid = signUid;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取  experience
	 *@return Integer  获得签到积分
	 */
	public Integer getExperience(){
		return this.experience;
	}

	/**
	 * 设置  experience
	 *@param experience  获得签到积分
	 */
	public void setExperience(Integer experience){
		this.experience = experience;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}