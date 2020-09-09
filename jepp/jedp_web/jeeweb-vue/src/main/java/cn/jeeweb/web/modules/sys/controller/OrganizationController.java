package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.common.helper.VueTreeHelper;
import cn.jeeweb.web.modules.sys.entity.Organization;
import cn.jeeweb.web.modules.sys.service.IOrganizationService;
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
@RequestMapping("/sys/organization")
@ViewPrefix("modules/sys/organization")
@RequiresPathPermission("sys:organization")
@Log(title = "部门管理")
public class OrganizationController extends BaseBeanController<Organization> {

    @Autowired
    private IOrganizationService organizationService;


    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "list")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void list(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<Organization> entityWrapper = new EntityWrapper<Organization>(entityClass);
        entityWrapper.setTableAlias("t.");
        //加入条件
        String keyword=request.getParameter("keyword");
        if (!StringUtils.isEmpty(keyword)){
            entityWrapper.like("name",keyword);
        }
        List<Organization> treeNodeList = organizationService.selectTreeList(entityWrapper);
        List<VueTreeHelper.VueTreeNode> vueTreeNodes = VueTreeHelper.create().sort(treeNodeList,"id,name,parentId,parentIds,remarks");
        String content = JSON.toJSONString(vueTreeNodes);
        StringUtils.printJson(response, content);
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(Organization entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        organizationService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        Organization entity = organizationService.selectById(id);
        model.addAttribute("data", entity);
        return displayModelAndView ("edit");
    }

    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(Organization entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        organizationService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        organizationService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        organizationService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }
}