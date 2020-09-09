package cn.jeeweb.bbs.modules.product.entity;

import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.alibaba.fastjson.annotation.JSONField;
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
 * @title: 购买产品实体
 * @description: 购买产品实体
 * @author: 王存见
 * @date: 2018-09-25 09:51:47
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("product_purchase")
@SuppressWarnings("serial")
public class ProductPurchase extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**productId*/
    @TableField(value = "product_id")
	private String productId;
    /**产品名称*/
    @TableField(value = "name")
	private String name;
    /**expiry_time*/
    @TableField(value = "expiry_time")
	@JSONField(format="yyyy-MM-dd")
	private Date expiryTime;
    /**付款金额*/
    @TableField(value = "price")
	private BigDecimal price;
    /**用户ID*/
    @TableField(value = "uid")
	private String uid;
    /**创建时间*/
    @TableField(value = "create_date")
	private Date createDate;
    /**更新时间*/
    @TableField(value = "update_date")
	private Date updateDate;

	@TableField(exist = false)
	private Product product;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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
	 * 获取  expiryTime
	 *@return String  expiry_time
	 */
	public Date getExpiryTime(){
		return this.expiryTime;
	}

	/**
	 * 设置  expiryTime
	 *@param expiryTime  expiry_time
	 */
	public void setExpiryTime(Date expiryTime){
		this.expiryTime = expiryTime;
	}
	/**
	 * 获取  price
	 *@return Double  付款金额
	 */
	public BigDecimal getPrice(){
		return this.price;
	}

	/**
	 * 设置  price
	 *@param price  付款金额
	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	/**
	 * 获取  uid
	 *@return String  用户ID
	 */
	public String getUid(){
		return this.uid;
	}

	/**
	 * 设置  uid
	 *@param uid  用户ID
	 */
	public void setUid(String uid){
		this.uid = uid;
	}
	/**
	 * 获取  createDate
	 *@return Date  创建时间
	 */
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 * 设置  createDate
	 *@param createDate  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 * 获取  updateDate
	 *@return Date  更新时间
	 */
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 * 设置  updateDate
	 *@param updateDate  更新时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}