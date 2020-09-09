package cn.jeeweb.generator.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.controller.BaseCRUDController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.generator.common.definition.DefinitionUtils;
import cn.jeeweb.generator.common.definition.data.Definition;
import cn.jeeweb.generator.entity.DataSource;
import cn.jeeweb.generator.service.IDataSourceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Title: 数据源
 * @Description: 数据源
 * @author jeeweb
 * @date 2017-05-10 12:01:57
 * @version V1.0
 *
 */
@RestController
@ViewPrefix("modules/generator/datasource")
@RequestMapping("${admin.url.prefix}/generator/datasource")
public class DataSourceController extends BaseBeanController<DataSource> {

	@Autowired
	private IDataSourceService dataSourceService;


	@GetMapping
	public ModelAndView list(Model model) {
		List<Definition> definitionList= DefinitionUtils.getDefinitionUtils().getDefinitionList();
		String definitionFormatterValue=	StringUtils.toFormatterValue(definitionList,"name","dbType");
		model.addAttribute("definitionFormatterValue",definitionFormatterValue);
		return displayModelAndView("list");
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @param response
	 * @throws java.io.IOExceptionf
	 */
	@RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
	public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
		EntityWrapper<DataSource> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		// 预处理
		QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<DataSource> pagejson = new PageResponse<>(dataSourceService.list(queryable,entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response,content);
	}
	public void preEdit(Model model) {
		List<Definition> definitionList=DefinitionUtils.getDefinitionUtils().getDefinitionList();
		model.addAttribute("definitionList",definitionList);
	}
	@GetMapping(value = "add")
	public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("data", new DataSource());
		preEdit(model);
		return displayModelAndView ("edit");
	}

	@PostMapping("add")
	public Response add(DataSource entity, BindingResult result,
						HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		dataSourceService.insert(entity);
		return Response.ok("添加成功");
	}

	@GetMapping(value = "{id}/update")
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
							   HttpServletResponse response) {
		DataSource entity = dataSourceService.selectById(id);
		model.addAttribute("data", entity);
		preEdit(model);
		return displayModelAndView ("edit");
	}

	@PostMapping("{id}/update")
	public Response update(DataSource entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		dataSourceService.insertOrUpdate(entity);
		return Response.ok("更新成功");
	}

	@PostMapping("{id}/delete")
	public Response delete(@PathVariable("id") String id) {
		dataSourceService.deleteById(id);
		return Response.ok("删除成功");
	}

	@PostMapping("batch/delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		dataSourceService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

	@GetMapping(value = "dataSourceParameter")
	public Response dataSourceParameter(@RequestParam String dbType) {
		Definition definition= DefinitionUtils.getDefinitionUtils().getDefinition(dbType);
		return Response.ok().putObject(definition);
	}

	@PostMapping(value = "testConnect")
	public Response testConnect(DataSource dataSource) {
		dataSourceService.testConnect(dataSource);
		return Response.ok();
	}

}