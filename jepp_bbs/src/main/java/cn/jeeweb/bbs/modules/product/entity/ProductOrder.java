package cn.jeeweb.bbs.modules.product.entity;

import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.product.entity
 * @title: 产品订单实体
 * @description: 产品订单实体
 * @author: 王存见
 * @date: 2018-09-19 10:42:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("product_order")
@SuppressWarnings("serial")
public class ProductOrder extends AbstractEntity<String> {
	/**订单状态，1未付款、2.已付款，3.交易成功，4.交易关闭*/
	public final static String order_status_non_payment = "1";
	public final static String order_status_account_paid = "2";
	public final static String order_status_account_success = "3";
	public final static String order_status_account_close = "4";

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**产品ID*/
    @TableField(value = "product_id")
	private String productId;
	/**产品名称*/
	@TableField(value = "name")
	private String name;
	@TableField(value = "create_by", el = "createBy.id", fill = FieldFill.INSERT)
	protected User createBy; // 创建者
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
    /**支付时间*/
    @TableField(value = "pay_date")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date payDate;
    /**用户ID*/
    @TableField(value = "uid")
	private String uid;
    /**关闭时间*/
    @TableField(value = "close_time")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date closeTime;
    /**订单状态，1未付款、2.已付款，3.交易成功，4.交易关闭*/
    @TableField(value = "order_status")
	private String orderStatus;
    /**支付金额*/
    @TableField(value = "total_amount")
	private BigDecimal totalAmount;

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
	/**
	 * 获取  projectId
	 *@return String  产品ID
	 */

	public String getProductId() {
		return productId;
	}
	/**
	 * 设置  projectId
	 *@param projectId  产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取  payDate
	 *@return Date  支付时间
	 */
	public Date getPayDate(){
		return this.payDate;
	}

	/**
	 * 设置  payDate
	 *@param payDate  支付时间
	 */
	public void setPayDate(Date payDate){
		this.payDate = payDate;
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
	 * 获取  closeTime
	 *@return Date  关闭时间
	 */
	public Date getCloseTime(){
		return this.closeTime;
	}

	/**
	 * 设置  closeTime
	 *@param closeTime  关闭时间
	 */
	public void setCloseTime(Date closeTime){
		this.closeTime = closeTime;
	}
	/**
	 * 获取  orderStatus
	 *@return String  订单状态，1未付款、2.已付款，3.交易成功，4.交易关闭
	 */
	public String getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 * 设置  orderStatus
	 *@param orderStatus  订单状态，1未付款、2.已付款，3.交易成功，4.交易关闭
	 */
	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取  totalAmount
	 *@return BigDecimal  支付金额
	 */
	public BigDecimal getTotalAmount(){
		return this.totalAmount;
	}

	/**
	 * 设置  totalAmount
	 *@param totalAmount  支付金额
	 */
	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount = totalAmount;
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