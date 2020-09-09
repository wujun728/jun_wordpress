package cn.jeeweb.bbs.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.jeeweb.bbs.common.entity.DataEntity;
import java.lang.String;
import cn.jeeweb.beetl.tags.dict.DictUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @Title 用户实体
 * @Description:
 * @author 王存见
 * @date 2016-12-03 21:31:50
 * @version V1.0
 *
 */
@TableName("sys_user")
@SuppressWarnings("serial")
public class User extends DataEntity<String> {

	/**
	 * 是否锁定（1：正常；-1：删除；0：锁定；）
	 */
	public static final String STATUS_DELETE = "-1";
	public static final String STATUS_LOCKED = "0";
	public static final String STATUS_NORMAL = "1";

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	// 姓名
	@Excel(name = "用户名", orderNum = "1")
	private String username;
	// 用户名
	@Excel(name = "姓名", orderNum = "0")
	private String realname;
	// 头像
	private String portrait;
	// 密码
	private String password;
	// 盐
	private String salt;
	// 邮件
	@Excel(name = "邮件", orderNum = "3", width = 20.0D)
	private String email;
	// 邮件是否激活
	@Excel(name = "是否激活",replace= {"是_1", "否_0"}, orderNum = "4")
	@TableField(value = "email_activate")
	private String emailActivate;
	// 联系电话
	@Excel(name = "联系电话", orderNum = "2", width = 20.0D )
	private String phone;

	@TableField(value = "vip_level")
	private String vipLevel;

	@TableField(value = "auth")
	private String auth;

	@TableField(value = "auth_info")
	private String authInfo;
	/**悬赏飞吻*/
	@TableField(value = "experience")
	private Integer experience;
	/**性别*/
	@TableField(value = "sex")
	private String sex;
	/**城市*/
	@TableField(value = "city")
	private String city;

	/**签名*/
	@TableField(value = "signature")
	private String signature;
	/**
	 * 系统用户的状态
	 */
	private String status;

	public void setDefault(){
		this.emailActivate = "0";
		this.vipLevel = "0";
		this.status = STATUS_NORMAL;
		this.auth = "0";
		this.experience = 200; //初始化200
		this.sex = "1";
	}

	/**
	 * 获取 username
	 *
	 * @return: String username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 设置 username
	 *
	 * @param: username
	 *             username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取 password
	 *
	 * @return: String password
	 */
	public String getPassword() {
		return this.password;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	/**
	 * 设置 password
	 *
	 * @param: password
	 *             password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取 id
	 *
	 * @return: String id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 id
	 *
	 * @param: id
	 *             id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 salt
	 *
	 * @return: String salt
	 */
	public String getSalt() {
		return this.salt;
	}

	/**
	 * 设置 salt
	 *
	 * @param: salt
	 *             salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmailActivate() {
		return emailActivate;
	}

	public void setEmailActivate(String emailActivate) {
		this.emailActivate = emailActivate;
	}
}
