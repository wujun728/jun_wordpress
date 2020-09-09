package cn.jeeweb.bbs.modules.posts.entity;

import cn.jeeweb.bbs.common.constant.DataBaseConstant;
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
 * @package cn.jeeweb.bbs.modules.bbs.entity
 * @title: 主题实体
 * @description: 主题实体
 * @author: 王存见
 * @date: 2018-08-29 17:50:29
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("posts")
@SuppressWarnings("serial")
public class Posts extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**栏目分类*/
    @TableField(value = "column")
	private String column;
    /**title*/
    @TableField(value = "title")
	private String title;
    /**所属产品*/
    @TableField(value = "project")
	private String project;
    /**版本号*/
    @TableField(value = "version")
	private String version;
    /**浏览器*/
    @TableField(value = "browser")
	private String browser;
    /**详细描述*/
    @TableField(value = "content")
	private String content;
    /**悬赏飞吻*/
    @TableField(value = "experience")
	private Integer experience;
    /**uid*/
    @TableField(value = "uid")
	private String uid;
    /**发布时间*/
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "publish_time")
	private Date publishTime;
    /**阅读数*/
    @TableField(value = "read_count")
	private Integer readCount;
	/**置顶*/
	@TableField(value = "top")
    private Integer top;
	/**评论数*/
	@TableField(exist = false)
	private Integer commentCount;
	/**是否解决*/
	@TableField(value = "solved")
	private Integer solved;
	/**精华*/
	@TableField(value = "essence")
	private Integer essence;

	@TableField(exist = false)
	private User user;
	@TableField(value = "del_flag")
	private String delFlag; // 删除标记（0：正常；1：删除 ）

	public void setDefault(){
		/**阅读数*/
		this.readCount = 0;
		/**置顶*/
		this.top = 0;
		/**评论数*/
		this.commentCount= 0;
		/**是否解决*/
		this.solved= 0;
		/**精华*/
		this.essence= 0;
		/**财富值*/
		if (experience == null) {
			this.experience = 0;
		}
		this.delFlag = DataBaseConstant.DEL_FLAG_NORMAL;
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

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取  title
	 *@return String  title
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 设置  title
	 *@param title  title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取  project
	 *@return String  所属产品
	 */
	public String getProject(){
		return this.project;
	}

	/**
	 * 设置  project
	 *@param project  所属产品
	 */
	public void setProject(String project){
		this.project = project;
	}
	/**
	 * 获取  version
	 *@return String  版本号
	 */
	public String getVersion(){
		return this.version;
	}

	/**
	 * 设置  version
	 *@param version  版本号
	 */
	public void setVersion(String version){
		this.version = version;
	}
	/**
	 * 获取  browser
	 *@return String  浏览器
	 */
	public String getBrowser(){
		return this.browser;
	}

	/**
	 * 设置  browser
	 *@param browser  浏览器
	 */
	public void setBrowser(String browser){
		this.browser = browser;
	}
	/**
	 * 获取  content
	 *@return String  详细描述
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * 设置  content
	 *@param content  详细描述
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取  experience
	 *@return String  悬赏飞吻
	 */
	public Integer getExperience(){
		return this.experience;
	}

	/**
	 * 设置  experience
	 *@param experience  悬赏飞吻
	 */
	public void setExperience(Integer experience){
		this.experience = experience;
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
	/**
	 * 获取  publishTime
	 *@return Date  发布时间
	 */
	public Date getPublishTime(){
		return this.publishTime;
	}

	/**
	 * 设置  publishTime
	 *@param publishTime  发布时间
	 */
	public void setPublishTime(Date publishTime){
		this.publishTime = publishTime;
	}
	/**
	 * 获取  readCount
	 *@return Integer  阅读数
	 */
	public Integer getReadCount(){
		return this.readCount;
	}

	/**
	 * 设置  readCount
	 *@param readCount  阅读数
	 */
	public void setReadCount(Integer readCount){
		this.readCount = readCount;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getSolved() {
		return solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public Integer getEssence() {
		return essence;
	}

	public void setEssence(Integer essence) {
		this.essence = essence;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}