package cn.jeeweb.generator.entity;

import cn.jeeweb.common.mvc.entity.AbstractEntity;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: CodegenTableEntity
 * @Description:代码生成的字段信息
 * @author: 王存见
 * @date: 2017年2月27日 下午5:35:23
 * 
 * @Copyright: 2017 www.jeeweb Inc. All rights reserved.
 *
 */
@TableName("generator_table")
public class Table extends AbstractEntity<String> implements java.io.Serializable {

	@TableId(value = "id", type = IdType.UUID)
	private String id;
	//数据源id
	@TableField("source_id")
	private String sourceId;
	@TableField("title")
	private String title;
	@TableField("table_name")
	private String tableName;
	@TableField("class_name")
	private String className;
	@TableField("table_type")
	private String tableType;
	@TableField("table_pk_type")
	private String tablePKType;
	@TableField("sync_database")
	private Boolean syncDatabase;
	@TableField("is_test")
	private Boolean test;
	@TableField(exist = false)
	private String parentField;
	@TableField("remarks")
	private String remarks;


	@TableField(exist = false)
	private List<Column> columns = new ArrayList<Column>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/** 实体名称 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getTablePKType() {
		return tablePKType;
	}

	public void setTablePKType(String tablePKType) {
		this.tablePKType = tablePKType;
	}

	public Boolean getSyncDatabase() {
		return syncDatabase;
	}

	public void setSyncDatabase(Boolean syncDatabase) {
		this.syncDatabase = syncDatabase;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Boolean getTest() {
		return test;
	}

	public void setTest(Boolean test) {
		this.test = test;
	}
}
