package cn.jeeweb.bbs.modules.sys.entity;

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
 * @package cn.jeeweb.bbs.modules.sys.entity
 * @title: 系统消息实体
 * @description: 系统消息实体
 * @author: 王存见
 * @date: 2018-09-03 15:10:32
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("sys_message")
@SuppressWarnings("serial")
public class Message extends AbstractEntity<String> {

    /**主键*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**标题*/
    @TableField(value = "title")
	private String title;
    /**模版内容*/
    @TableField(value = "content")
	private String content;
    /**是否阅读*/
    @TableField(value = "is_read")
	private Integer read;
    /**阅读的用户ID*/
    @TableField(value = "read_uid")
	private String readUid;
    /**阅读的人*/
    @TableField(value = "read_uname")
	private String readUname;
    /**阅读时间*/
    @TableField(value = "read_date")
	private Date readDate;
    /**send_date*/
    @TableField(value = "send_date")
	private Date sendDate;
	
	/**
	 * 获取  id
	 *@return String  主键
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * 设置  id
	 *@param id  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取  title
	 *@return String  标题
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 设置  title
	 *@param title  标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取  content
	 *@return String  模版内容
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * 设置  content
	 *@param content  模版内容
	 */
	public void setContent(String content){
		this.content = content;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	/**
	 * 获取  readUid
	 *@return String  阅读的用户ID
	 */
	public String getReadUid(){
		return this.readUid;
	}

	/**
	 * 设置  readUid
	 *@param readUid  阅读的用户ID
	 */
	public void setReadUid(String readUid){
		this.readUid = readUid;
	}
	/**
	 * 获取  readUname
	 *@return String  阅读的人
	 */
	public String getReadUname(){
		return this.readUname;
	}

	/**
	 * 设置  readUname
	 *@param readUname  阅读的人
	 */
	public void setReadUname(String readUname){
		this.readUname = readUname;
	}
	/**
	 * 获取  readDate
	 *@return Date  阅读时间
	 */
	public Date getReadDate(){
		return this.readDate;
	}

	/**
	 * 设置  readDate
	 *@param readDate  阅读时间
	 */
	public void setReadDate(Date readDate){
		this.readDate = readDate;
	}
	/**
	 * 获取  sendDate
	 *@return Date  send_date
	 */
	public Date getSendDate(){
		return this.sendDate;
	}

	/**
	 * 设置  sendDate
	 *@param sendDate  send_date
	 */
	public void setSendDate(Date sendDate){
		this.sendDate = sendDate;
	}
	
}