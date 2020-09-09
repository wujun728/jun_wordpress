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
 * @title: 登陆状态实体
 * @description: 登陆状态状态实体
 * @author: 王存见
 * @date: 2018-09-05 18:45:25
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("sign_in_status")
@SuppressWarnings("serial")
public class SignInStatus extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**最后签到时间*/
    @TableField(value = "last_sign_time")
	private Date lastSignTime;
    /**qiand*/
    @TableField(value = "sign_uid")
	private String signUid;
	/**连续签到天数*/
	@TableField(value = "sign_day")
	private Integer signDay;
    /**累计获得经验*/
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
	 * 获取  lastSignTime
	 *@return Date  最后签到时间
	 */
	public Date getLastSignTime(){
		return this.lastSignTime;
	}

	/**
	 * 设置  lastSignTime
	 *@param lastSignTime  最后签到时间
	 */
	public void setLastSignTime(Date lastSignTime){
		this.lastSignTime = lastSignTime;
	}
	/**
	 * 获取  signUid
	 *@return String  qiand
	 */
	public String getSignUid(){
		return this.signUid;
	}

	/**
	 * 设置  signUid
	 *@param signUid  qiand
	 */
	public void setSignUid(String signUid){
		this.signUid = signUid;
	}


	public Integer getSignDay() {
		return signDay;
	}

	public void setSignDay(Integer signDay) {
		this.signDay = signDay;
	}

	/**
	 * 获取  experience
	 *@return Integer  累计获得经验
	 */
	public Integer getExperience(){
		return this.experience;
	}

	/**
	 * 设置  experience
	 *@param experience  累计获得经验
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