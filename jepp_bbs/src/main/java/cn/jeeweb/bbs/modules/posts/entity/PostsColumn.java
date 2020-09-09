package cn.jeeweb.bbs.modules.posts.entity;

import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.entity
 * @title: 帖子栏目实体
 * @description: 帖子栏目实体
 * @author: 王存见
 * @date: 2018-08-30 22:50:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@TableName("posts_column")
@SuppressWarnings("serial")
public class PostsColumn extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**名称*/
    @TableField(value = "name")
	private String name;
    /**别名*/
    @TableField(value = "alias")
	private String alias;
    /**编码*/
    @TableField(value = "code")
	private String code;
    /**sort*/
    @TableField(value = "sort")
	private Integer sort;

	@TableField(value = "auth")
	private String auth;
	
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
	 *@return String  名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 设置  name
	 *@param name  名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取  alias
	 *@return String  别名
	 */
	public String getAlias(){
		return this.alias;
	}

	/**
	 * 设置  alias
	 *@param alias  别名
	 */
	public void setAlias(String alias){
		this.alias = alias;
	}
	/**
	 * 获取  code
	 *@return String  编码
	 */
	public String getCode(){
		return this.code;
	}

	/**
	 * 设置  code
	 *@param code  编码
	 */
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * 获取  sort
	 *@return Integer  sort
	 */
	public Integer getSort(){
		return this.sort;
	}

	/**
	 * 设置  sort
	 *@param sort  sort
	 */
	public void setSort(Integer sort){
		this.sort = sort;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}