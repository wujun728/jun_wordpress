package cn.jeeweb.bbs.modules.posts.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.posts.service.IPostsColumnService;
import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
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


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.controller
 * @title: 帖子栏目控制器
 * @description: 帖子栏目控制器
 * @author: 王存见
 * @date: 2018-08-30 22:50:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/posts/posts/column")
public class PostsColumnController extends BaseBeanController<PostsColumn> {

    @Autowired
    private IPostsColumnService postsColumnService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView ("modules/posts/posts/column/list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "sort=desc")
    private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<PostsColumn> entityWrapper = new EntityWrapper<>(entityClass);
        entityWrapper.orderBy("sort");
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<PostsColumn> pagejson = new PageResponse<>(postsColumnService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new PostsColumn());
        return new ModelAndView ("modules/posts/posts/column/edit");
    }

    @PostMapping("add")
    public Response add(PostsColumn entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        PostsColumn entity = postsColumnService.selectById(id);
        model.addAttribute("data", entity);
        return new ModelAndView ("modules/posts/posts/column/edit");
    }

    @PostMapping("{id}/update")
    public Response update(PostsColumn entity, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("/save")
    public Response doSave(PostsColumn entity, HttpServletRequest request, HttpServletResponse response,
                                 BindingResult result) {
        if (hasError(entity, result)) {
            // 错误提示
            String errorMsg = errorMsg(result);
            if (!StringUtils.isEmpty(errorMsg)) {
                return Response.error(ResponseError.NORMAL_ERROR,errorMsg);
            } else {
                return Response.error(ResponseError.NORMAL_ERROR,"保存失败");
            }
        }
        try {
            if (StringUtils.isEmpty(entity.getId())) {
                postsColumnService.insert(entity);
            } else {
                postsColumnService.insertOrUpdate(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
        }
        return Response.ok("保存成功");
    }

    @GetMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        try {
            postsColumnService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"删除失败");
        }
        return Response.ok("删除成功");
    }


}