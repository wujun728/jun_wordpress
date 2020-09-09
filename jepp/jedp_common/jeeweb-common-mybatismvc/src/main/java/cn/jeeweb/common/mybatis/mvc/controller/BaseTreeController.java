package cn.jeeweb.common.mybatis.mvc.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.mvc.entity.AbstractEntity;
import cn.jeeweb.common.mvc.entity.tree.BootstrapTreeHelper;
import cn.jeeweb.common.mvc.entity.tree.BootstrapTreeNode;
import cn.jeeweb.common.mvc.entity.tree.TreeNode;
import cn.jeeweb.common.mvc.entity.tree.TreeSortUtil;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.mybatis.mvc.service.ITreeCommonService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.QueryPropertyPreFilter;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public abstract class BaseTreeController<Entity extends AbstractEntity<ID> & TreeNode<ID>, ID extends Serializable>
		extends BaseCRUDController<Entity, ID> {

	ITreeCommonService<Entity, ID> treeCommonService;

	@Autowired
	public void treeCommonService(ITreeCommonService<Entity, ID> treeCommonService) {
		this.treeCommonService = treeCommonService;
		setCommonService((ICommonService<Entity>) treeCommonService);
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public void treeData(Queryable queryable,
			@RequestParam(value = "nodeid", required = false, defaultValue = "") ID nodeid,
			@RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<Entity> entityWrapper = new EntityWrapper<Entity>(entityClass);
		entityWrapper.setTableAlias("t.");
		List<Entity> treeNodeList = null;
		if (!async) { // 非异步 查自己和子子孙孙
			treeNodeList = treeCommonService.selectTreeList(queryable, entityWrapper);
			TreeSortUtil.create().sort(treeNodeList).async(treeNodeList);
		} else { // 异步模式只查自己
			// queryable.addCondition("parentId", nodeid);
			if (ObjectUtils.isNullOrEmpty(nodeid)) {
				// 判断的应该是多个OR条件
				entityWrapper.isNull("parentId");
			} else {
				entityWrapper.eq("parentId", nodeid);
			}
			treeNodeList = treeCommonService.selectTreeList(queryable, entityWrapper);
			TreeSortUtil.create().sync(treeNodeList);
		}
		PropertyPreFilterable propertyPreFilterable = new QueryPropertyPreFilter();
		propertyPreFilterable.addQueryProperty("id", "name", "expanded", "hasChildren", "leaf", "loaded", "level",
				"parentId");
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<Entity> pagejson = new PageResponse<Entity>(treeNodeList);
		String content = JSON.toJSONString(pagejson);
		StringUtils.printJson(response, content);
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxTreeList")
	private void ajaxTreeList(Queryable queryable,
			@RequestParam(value = "nodeid", required = false, defaultValue = "") ID nodeid,
			@RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
			HttpServletRequest request, HttpServletResponse response, PropertyPreFilterable propertyPreFilterable)
			throws IOException {
		EntityWrapper<Entity> entityWrapper = new EntityWrapper<Entity>(entityClass);
		entityWrapper.setTableAlias("t");
		preAjaxList(queryable, entityWrapper, request, response);

		List<Entity> treeNodeList = null;
		if (!async) { // 非异步 查自己和子子孙孙
			treeNodeList = treeCommonService.selectTreeList(queryable, entityWrapper);
			TreeSortUtil.create().sort(treeNodeList).async(treeNodeList);
		} else { // 异步模式只查自己
			// queryable.addCondition("parentId", nodeid);
			if (ObjectUtils.isNullOrEmpty(nodeid)) {
				// 判断的应该是多个OR条件
				entityWrapper.isNull("parentId");
			} else {
				entityWrapper.eq("parentId", nodeid);
			}
			treeNodeList = treeCommonService.selectTreeList(queryable, entityWrapper);
			TreeSortUtil.create().sync(treeNodeList);
		}
		propertyPreFilterable.addQueryProperty("id", "expanded", "hasChildren", "leaf", "loaded", "level", "parentId");
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<Entity> pagejson = new PageResponse<Entity>(treeNodeList);
		String content = JSON.toJSONString(pagejson);
		StringUtils.printJson(response, content);
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "bootstrapTreeData")
	private void bootstrapTreeData(Queryable queryable,
                                   @RequestParam(value = "nodeid", required = false, defaultValue = "") ID nodeid, HttpServletRequest request,
                                   HttpServletResponse response, PropertyPreFilterable propertyPreFilterable) throws IOException {
		EntityWrapper<Entity> entityWrapper = new EntityWrapper<Entity>(entityClass);
		entityWrapper.setTableAlias("t.");
		List<Entity> treeNodeList = treeCommonService.selectTreeList(queryable, entityWrapper);
		List<BootstrapTreeNode> bootstrapTreeNodes = BootstrapTreeHelper.create().sort(treeNodeList);
		propertyPreFilterable.addQueryProperty("text", "href", "tags", "nodes");
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		String content = JSON.toJSONString(bootstrapTreeNodes, filter);
		StringUtils.printJson(response, content);
	}

}
