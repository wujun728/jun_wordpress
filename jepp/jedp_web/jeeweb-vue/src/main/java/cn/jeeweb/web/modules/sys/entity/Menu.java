package cn.jeeweb.web.modules.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import cn.jeeweb.common.mybatis.mvc.entity.TreeEntity;

@TableName("sys_menu")
@SuppressWarnings("serial")
public class Menu extends TreeEntity<Menu> {
	@TableField(value = "permission")
	private String permission;
	/**资源路径*/
	/**菜单类型*/
	@TableField(value = "type")
	private String type;
	/**路径编码*/
	@TableField(value = "path")
	private String path;
	/**访问路由*/
	@TableField(value = "url")
	private String url;
	/**是否显示*/
	@TableField(value = "enabled")
	private Short enabled;
	/**排序*/
	@TableField(value = "sort")
	private Integer sort;
	/**图标*/
	@TableField(value = "icon")
	private String icon;
	/**跳转URL*/
	@TableField(value = "redirect")
	private String redirect;
	/**是否缓存*/
	@TableField(value = "cacheable")
	private Boolean cacheable;
	/**是否需要认证*/
	@TableField(value = "require_auth")
	private Boolean requireAuth;
	/**前端资源路径*/
	@TableField(value = "component")
	private String component;
	/**摘要*/
	@TableField(value = "remarks")
	private String remarks;
	@TableField(value = "create_by", fill = FieldFill.INSERT)
	protected String createBy; // 创建者
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	protected Date createDate; // 创建日期
	@TableField(value = "update_by", fill = FieldFill.UPDATE)
	protected String updateBy; // 更新者
	@TableField(value = "update_date", fill = FieldFill.UPDATE)
	protected Date updateDate; // 更新日期
	@TableField(value = "del_flag", fill = FieldFill.INSERT)
	protected String delFlag = "0"; // 删除标记（0：正常；1：删除 ）


	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Boolean getCacheable() {
		return cacheable;
	}

	public void setCacheable(Boolean cacheable) {
		this.cacheable = cacheable;
	}

	public Boolean getRequireAuth() {
		return requireAuth;
	}

	public void setRequireAuth(Boolean requireAuth) {
		this.requireAuth = requireAuth;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
