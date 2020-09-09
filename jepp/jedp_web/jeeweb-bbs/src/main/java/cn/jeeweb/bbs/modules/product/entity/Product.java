package cn.jeeweb.bbs.modules.product.entity;

import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.product.entity
 * @title: 产品管理实体
 * @description: 产品管理实体
 * @author: 王存见
 * @date: 2018-09-19 10:41:56
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("product")
@SuppressWarnings("serial")
public class Product extends AbstractEntity<String> {

    /**id*/
    @TableField(value = "id")
	private String id;
    /**产品名称*/
    @TableField(value = "name")
	private String name;
    /**描述*/
    @TableField(value = "description")
	private String description;
    /**产品版本*/
    @TableField(value = "version")
	private String version;
    /**授权期限*/
    @TableField(value = "authorization_period")
	private String authorizationPeriod;
    /**金额*/
    @TableField(value = "total_amount")
	private BigDecimal totalAmount;
    /**折扣*/
    @TableField(value = "discount_amount")
	private BigDecimal discountAmount;
    /**折扣描述*/
    @TableField(value = "discount_description")
	private String discountDescription;
	
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
	 * 获取  name
	 *@return String  产品名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 设置  name
	 *@param name  产品名称
	 */
	public void setName(String name){
		this.name = name;
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
	 * 获取  version
	 *@return String  产品版本
	 */
	public String getVersion(){
		return this.version;
	}

	/**
	 * 设置  version
	 *@param version  产品版本
	 */
	public void setVersion(String version){
		this.version = version;
	}

	public String getAuthorizationPeriod() {
		return authorizationPeriod;
	}

	public void setAuthorizationPeriod(String authorizationPeriod) {
		this.authorizationPeriod = authorizationPeriod;
	}

	/**
	 * 获取  totalAmount
	 *@return BigDecimal  金额
	 */
	public BigDecimal getTotalAmount(){
		return this.totalAmount;
	}

	/**
	 * 设置  totalAmount
	 *@param totalAmount  金额
	 */
	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取  discountAmount
	 *@return BigDecimal  折扣
	 */
	public BigDecimal getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 * 设置  discountAmount
	 *@param discountAmount  折扣
	 */
	public void setDiscountAmount(BigDecimal discountAmount){
		this.discountAmount = discountAmount;
	}
	/**
	 * 获取  discountDescription
	 *@return String  折扣描述
	 */
	public String getDiscountDescription(){
		return this.discountDescription;
	}

	/**
	 * 设置  discountDescription
	 *@param discountDescription  折扣描述
	 */
	public void setDiscountDescription(String discountDescription){
		this.discountDescription = discountDescription;
	}
	
}