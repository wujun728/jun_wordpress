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
 * @title: example实体
 * @description: example实体
 * @author: 王存见
 * @date: 2018-09-04 16:46:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("example")
@SuppressWarnings("serial")
public class Example extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**案列标题*/
    @TableField(value = "title")
	private String title;
    /**案例链接*/
    @TableField(value = "link")
	private String link;
    /**描述*/
    @TableField(value = "description")
	private String description;
    /**cover*/
    @TableField(value = "cover")
	private String cover;
    /**提交人*/
    @TableField(value = "report_uid")
	private String reportUid;
    /**report_date*/
    @TableField(value = "report_date")
	private Date reportDate;
    /**删除标记*/
    @TableField(value = "del_flag")
	private String delFlag;
	/**点赞数*/
	@TableField(exist =false)
    private Integer praiseCount;
	@TableField(exist =false)
	private Integer praise;

	@TableField(exist =false)
	private User user;

    public void setDefault(){
		delFlag = "0";
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
	 * 获取  title
	 *@return String  案列标题
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 设置  title
	 *@param title  案列标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取  link
	 *@return String  案例链接
	 */
	public String getLink(){
		return this.link;
	}

	/**
	 * 设置  link
	 *@param link  案例链接
	 */
	public void setLink(String link){
		this.link = link;
	}
	/**
	 * 获取  description
	 *@return String  描述
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 设置  description
	 *@param description  描述
	 */
	public void setDescription(String description){
		this.description = description;
	}
	/**
	 * 获取  cover
	 *@return String  cover
	 */
	public String getCover(){
		return this.cover;
	}

	/**
	 * 设置  cover
	 *@param cover  cover
	 */
	public void setCover(String cover){
		this.cover = cover;
	}
	/**
	 * 获取  reportUid
	 *@return String  提交人
	 */
	public String getReportUid(){
		return this.reportUid;
	}

	/**
	 * 设置  reportUid
	 *@param reportUid  提交人
	 */
	public void setReportUid(String reportUid){
		this.reportUid = reportUid;
	}
	/**
	 * 获取  reportDate
	 *@return Date  report_date
	 */
	public Date getReportDate(){
		return this.reportDate;
	}

	/**
	 * 设置  reportDate
	 *@param reportDate  report_date
	 */
	public void setReportDate(Date reportDate){
		this.reportDate = reportDate;
	}
	/**
	 * 获取  delFlag
	 *@return String  删除标记
	 */
	public String getDelFlag(){
		return this.delFlag;
	}

	/**
	 * 设置  delFlag
	 *@param delFlag  删除标记
	 */
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}