package cn.jeeweb.web.modules.sms.entity;

import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.sms.entity
 * @title: 发送日志实体
 * @description: 发送日志实体
 * @author: 王存见
 * @date: 2018-09-14 09:47:53
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("sms_send_log")
@SuppressWarnings("serial")
public class SmsSendLog extends AbstractEntity<String> {

	public static final String SMS_SEND_STATUS_SUCCESS="1";
	public static final String SMS_SEND_STATUS_FAIL="-1";
	public static final String SMS_SEND_STATUS_IN="0";
    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**联系电话*/
    @TableField(value = "phone")
	private String phone;
    /**模板名称*/
    @TableField(value = "template_name")
	private String templateName;
    /**发送数据*/
    @TableField(value = "send_data")
	private String sendData;
    /**send_code*/
    @TableField(value = "send_code")
	private String sendCode;
    /**try_num*/
    @TableField(value = "try_num")
	private Integer tryNum;
    /**发送状态*/
    @TableField(value = "status")
	private String status; // 0发送中，1成功，-1失败
    /**发送响应消息ID*/
    @TableField(value = "smsid")
	private String smsid;
    /**返回码*/
    @TableField(value = "code")
	private String code;
    /**返回消息*/
    @TableField(value = "msg")
	private String msg;
    /**删除标记（0：正常；1：删除）*/
    @TableField(value = "del_flag")
	private String delFlag;
    /**响应时间*/
    @TableField(value = "response_date")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date responseDate;
	
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
	 * 获取  phone
	 *@return String  联系电话
	 */
	public String getPhone(){
		return this.phone;
	}

	/**
	 * 设置  phone
	 *@param phone  联系电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 获取  templateName
	 *@return String  模板名称
	 */
	public String getTemplateName(){
		return this.templateName;
	}

	/**
	 * 设置  templateName
	 *@param templateName  模板名称
	 */
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	/**
	 * 获取  sendData
	 *@return String  发送数据
	 */
	public String getSendData(){
		return this.sendData;
	}

	/**
	 * 设置  sendData
	 *@param sendData  发送数据
	 */
	public void setSendData(String sendData){
		this.sendData = sendData;
	}
	/**
	 * 获取  sendCode
	 *@return String  send_code
	 */
	public String getSendCode(){
		return this.sendCode;
	}

	/**
	 * 设置  sendCode
	 *@param sendCode  send_code
	 */
	public void setSendCode(String sendCode){
		this.sendCode = sendCode;
	}
	/**
	 * 获取  tryNum
	 *@return Integer  try_num
	 */
	public Integer getTryNum(){
		return this.tryNum;
	}

	/**
	 * 设置  tryNum
	 *@param tryNum  try_num
	 */
	public void setTryNum(Integer tryNum){
		this.tryNum = tryNum;
	}
	/**
	 * 获取  status
	 *@return String  发送状态
	 */
	public String getStatus(){
		return this.status;
	}

	/**
	 * 设置  status
	 *@param status  发送状态
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 * 获取  smsid
	 *@return String  发送响应消息ID
	 */
	public String getSmsid(){
		return this.smsid;
	}

	/**
	 * 设置  smsid
	 *@param smsid  发送响应消息ID
	 */
	public void setSmsid(String smsid){
		this.smsid = smsid;
	}
	/**
	 * 获取  code
	 *@return String  返回码
	 */
	public String getCode(){
		return this.code;
	}

	/**
	 * 设置  code
	 *@param code  返回码
	 */
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 获取  msg
	 *@return String  返回消息
	 */
	public String getMsg(){
		return this.msg;
	}

	/**
	 * 设置  msg
	 *@param msg  返回消息
	 */
	public void setMsg(String msg){
		this.msg = msg;
	}
	/**
	 * 获取  delFlag
	 *@return String  删除标记（0：正常；1：删除）
	 */
	public String getDelFlag(){
		return this.delFlag;
	}

	/**
	 * 设置  delFlag
	 *@param delFlag  删除标记（0：正常；1：删除）
	 */
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	/**
	 * 获取  responseDate
	 *@return Date  响应时间
	 */
	public Date getResponseDate(){
		return this.responseDate;
	}

	/**
	 * 设置  responseDate
	 *@param responseDate  响应时间
	 */
	public void setResponseDate(Date responseDate){
		this.responseDate = responseDate;
	}
	
}