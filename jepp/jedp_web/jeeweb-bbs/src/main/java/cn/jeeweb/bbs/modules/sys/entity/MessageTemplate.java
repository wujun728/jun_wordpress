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
 * @title: 消息模版实体
 * @description: 消息模版实体
 * @author: 王存见
 * @date: 2018-09-03 15:10:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("sys_message_template")
@SuppressWarnings("serial")
public class MessageTemplate extends AbstractEntity<String> {

    /**主键*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**模版名称*/
    @TableField(value = "name")
	private String name;
    /**模版编码*/
    @TableField(value = "code")
	private String code;
    /**模版标题*/
    @TableField(value = "template_title")
	private String templateTitle;
    /**模版内容*/
    @TableField(value = "template_content")
	private String templateContent;
	
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
	 * 获取  name
	 *@return String  模版名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 设置  name
	 *@param name  模版名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取  code
	 *@return String  模版编码
	 */
	public String getCode(){
		return this.code;
	}

	/**
	 * 设置  code
	 *@param code  模版编码
	 */
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 获取  templateTitle
	 *@return String  模版标题
	 */
	public String getTemplateTitle(){
		return this.templateTitle;
	}

	/**
	 * 设置  templateTitle
	 *@param templateTitle  模版标题
	 */
	public void setTemplateTitle(String templateTitle){
		this.templateTitle = templateTitle;
	}
	/**
	 * 获取  templateContent
	 *@return String  模版内容
	 */
	public String getTemplateContent(){
		return this.templateContent;
	}

	/**
	 * 设置  templateContent
	 *@param templateContent  模版内容
	 */
	public void setTemplateContent(String templateContent){
		this.templateContent = templateContent;
	}
	
}