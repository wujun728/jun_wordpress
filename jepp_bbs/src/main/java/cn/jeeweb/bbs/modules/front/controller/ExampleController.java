package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.example.entity.Example;
import cn.jeeweb.bbs.modules.example.entity.Praise;
import cn.jeeweb.bbs.modules.example.service.IExampleService;
import cn.jeeweb.bbs.modules.example.service.IPraiseService;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import cn.jeeweb.bbs.utils.PostsUtils;
import cn.jeeweb.bbs.utils.UserUtils;
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
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.example.controller
 * @title: example控制器
 * @description: example控制器
 * @author: 王存见
 * @date: 2018-09-04 16:46:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController("FrontExampleController")
@RequestMapping("example")
public class ExampleController extends BaseBeanController<Example> {

    @Autowired
    private IExampleService exampleService;


    @Autowired
    private IPraiseService praiseService;

    @GetMapping({"page/{page}","/","","{orderBy}/page/{page}","{orderBy}"})
    public ModelAndView index(Model model,
                              @PathVariable(value = "page",required = false) Integer page,
                              @PathVariable(value = "orderBy",required = false) String orderBy) {
        if (page == null){
            page = 1;
        }
        String listUrl="/example";
        if (StringUtils.isEmpty(orderBy)){
            orderBy = "publish";
        }else{
            listUrl+="/"+orderBy;
        }
        Page examplePageBean = listExamplePage(page,20,false,orderBy);
        model.addAttribute("examplePageBean",examplePageBean);
        model.addAttribute("orderBy",orderBy);
        model.addAttribute("listUrl",listUrl);
        return new ModelAndView("modules/front/example/index");
    }

    @GetMapping({"my"})
    public ModelAndView my(Model model,@PathVariable(value = "page",required = false) Integer page) {
        if (page == null){
            page = 1;
        }
        String listUrl="/example/my";
        Page examplePageBean = listExamplePage(page,20,true, "publish");
        model.addAttribute("examplePageBean",examplePageBean);
        model.addAttribute("listUrl",listUrl);
        return new ModelAndView("modules/front/example/my");
    }

    /**
     *  案列
     * @param current
     * @param size
     * @return
     */
    private Page listExamplePage(int current, int size, Boolean isSelf, String orderBy){
        //案列
        Page<Example> pageBean = new com.baomidou.mybatisplus.plugins.Page<Example>(
                current, size);
        EntityWrapper<Example> entityWrapper=  new EntityWrapper<>(Example.class);
        if (isSelf){
            entityWrapper.eq("e.report_uid", UserUtils.getUser().getId());
        }
        if (!StringUtils.isEmpty(orderBy)&&orderBy.equals("praise")) {
            entityWrapper.orderBy("praiseCount", false);
        }else{
            entityWrapper.orderBy("reportDate", false);
        }
        pageBean = exampleService.selectExamplePage(pageBean,entityWrapper, UserUtils.getUser().getId());
        return pageBean;
    }
    /**
     * 创建案例
     * @param entity
     * @param result
     * @param request
     * @param response
     * @return
     */
    @PostMapping("add")
    public Response add(Example entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
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
            entity.setReportDate(new Date());
            entity.setReportUid(UserUtils.getUser().getId());
            entity.setDelFlag("0");
            exampleService.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
        }
        return Response.ok("保存成功");
    }

    /**
     * 删除案例
     * @param id
     * @return
     */
    @PostMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        try {
            exampleService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"删除失败");
        }
        return Response.ok("删除成功");
    }

    @PostMapping("{id}/praise")
    public Response praise(@PathVariable("id") String id) {
        Response response = Response.ok("赞成功");
        try {
            EntityWrapper<Praise> entityWrapper = new EntityWrapper<>(Praise.class);
            entityWrapper.eq("exampleId",id);
            entityWrapper.eq("praiseUid",UserUtils.getUser().getId());
            if (praiseService.selectCount(entityWrapper) == 0) {
                Praise praise = new Praise();
                praise.setPraiseTime(new Date());
                praise.setPraiseUid(UserUtils.getUser().getId());
                praise.setExampleId(id);
                praiseService.insert(praise);
            }else{
                praiseService.delete(entityWrapper);
            }
            EntityWrapper<Praise> countEntityWrapper = new EntityWrapper<>(Praise.class);
            countEntityWrapper.eq("exampleId",id);
            response.put("praise",praiseService.selectCount(countEntityWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"赞失败");
        }
        return response;
    }

    @PostMapping("{id}/praiseUser")
    public Response praiseUser(@PathVariable("id") String id) {
        Response response = Response.ok("获取成功");
        try {
            Example example =  exampleService.selectById(id);
            List<Praise> praiseList= praiseService.selectPraiseList(id);
            response.put("title",example.getTitle());
            response.putList("data",praiseList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"赞失败");
        }
        return response;
    }


}