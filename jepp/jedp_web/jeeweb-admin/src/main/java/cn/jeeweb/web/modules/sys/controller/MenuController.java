package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mvc.entity.tree.BootstrapTreeHelper;
import cn.jeeweb.common.mvc.entity.tree.BootstrapTreeNode;
import cn.jeeweb.common.mvc.entity.tree.TreeSortUtil;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.QueryPropertyPreFilter;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sys.entity.Menu;
import cn.jeeweb.web.modules.sys.service.IMenuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/sys/menu")
@ViewPrefix("modules/sys/menu")
@RequiresPathPermission("sys:menu")
@Log(title = "菜单管理")
public class MenuController extends BaseBeanController<Menu> {

    @Autowired
    private IMenuService menuService;


    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return displayModelAndView("list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "sort=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<Menu> pagejson = new PageResponse<>(menuService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new Menu());
        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(Menu entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        menuService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        Menu entity = menuService.selectById(id);
        model.addAttribute("data", entity);
        return displayModelAndView ("edit");
    }

    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(Menu entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        menuService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        menuService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        menuService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "treeData")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void treeData(Queryable queryable,
                         @RequestParam(value = "nodeid", required = false, defaultValue = "") String nodeid,
                         @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<Menu>(entityClass);
        entityWrapper.setTableAlias("t.");
        List<Menu> treeNodeList = null;
        if (!async) { // 非异步 查自己和子子孙孙
            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
            TreeSortUtil.create().sort(treeNodeList).async(treeNodeList);
        } else { // 异步模式只查自己
            // queryable.addCondition("parentId", nodeid);
            if (ObjectUtils.isNullOrEmpty(nodeid)) {
                // 判断的应该是多个OR条件
                entityWrapper.isNull("parentId");
            } else {
                entityWrapper.eq("parentId", nodeid);
            }
            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
            TreeSortUtil.create().sync(treeNodeList);
        }
        PropertyPreFilterable propertyPreFilterable = new QueryPropertyPreFilter();
        propertyPreFilterable.addQueryProperty("id", "name", "expanded", "hasChildren", "leaf", "loaded", "level",
                "parentId");
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<Menu> pagejson = new PageResponse<Menu>(treeNodeList);
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
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void ajaxTreeList(Queryable queryable,
                             @RequestParam(value = "nodeid", required = false, defaultValue = "") String nodeid,
                             @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
                             HttpServletRequest request, HttpServletResponse response, PropertyPreFilterable propertyPreFilterable)
            throws IOException {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<Menu>(entityClass);
        entityWrapper.setTableAlias("t");

        List<Menu> treeNodeList = null;
        if (!async) { // 非异步 查自己和子子孙孙
            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
            TreeSortUtil.create().sort(treeNodeList).async(treeNodeList);
        } else { // 异步模式只查自己
            // queryable.addCondition("parentId", nodeid);
            if (ObjectUtils.isNullOrEmpty(nodeid)) {
                // 判断的应该是多个OR条件
                entityWrapper.isNull("parentId");
            } else {
                entityWrapper.eq("parentId", nodeid);
            }
            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
            TreeSortUtil.create().sync(treeNodeList);
        }
        propertyPreFilterable.addQueryProperty("id", "expanded", "hasChildren", "leaf", "loaded", "level", "parentId");
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<Menu> pagejson = new PageResponse<Menu>(treeNodeList);
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
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void bootstrapTreeData(Queryable queryable,
                                  @RequestParam(value = "nodeid", required = false, defaultValue = "") String nodeid, HttpServletRequest request,
                                  HttpServletResponse response, PropertyPreFilterable propertyPreFilterable) throws IOException {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<Menu>(entityClass);
        entityWrapper.setTableAlias("t.");
        List<Menu> treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
        List<BootstrapTreeNode> bootstrapTreeNodes = BootstrapTreeHelper.create().sort(treeNodeList);
        propertyPreFilterable.addQueryProperty("text", "href", "tags", "nodes");
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        String content = JSON.toJSONString(bootstrapTreeNodes, filter);
        StringUtils.printJson(response, content);
    }

    @GetMapping("{id}/generate/button")
    public ModelAndView generateButton(Model model) {
        model.addAttribute("data",new Menu());
        model.addAttribute("parentPermission","");
        model.addAttribute("permissions","");
        return displayModelAndView ("generate_button");
    }
    @PostMapping("{id}/generate/button")
    @Log(logType = LogType.OTHER, title = "生成按钮")
    @RequiresMethodPermissions("generate:button")
    public Response generateButton(@PathVariable("id") String id,
                                   @RequestParam("parentPermission") String parentPermission,
                                   @RequestParam("permissions") String[] permissions) {
        String[] permissionTitles = new String[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            String permissionTitle = DictUtils.getDictLabel(permissions[i],"permissionTypes","");
            permissionTitles[i] = permissionTitle;
        }
        menuService.generateButton(id, parentPermission, permissions, permissionTitles);
        return Response.ok("生成成功");
    }

}