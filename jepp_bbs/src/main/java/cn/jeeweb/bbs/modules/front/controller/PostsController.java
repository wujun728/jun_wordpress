package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.common.constant.DataBaseConstant;
import cn.jeeweb.bbs.modules.posts.entity.PostsCollection;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.entity.PostsColumn;
import cn.jeeweb.bbs.modules.posts.service.IPostsCollectionService;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
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
import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.common.utils.jcaptcha.JCaptcha;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
 * @package cn.jeeweb.bbs.modules.bbs.controller
 * @title: 主题控制器
 * @description: 主题控制器
 * @author: 王存见
 * @date: 2018-08-29 17:50:29
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController("FrontPostsController")
@RequestMapping("posts")
public class PostsController extends BaseBeanController<Posts> {

    @Autowired
    private IPostsService postsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostsCommentService postsCommentService;

    @Autowired
    private IPostsCollectionService postsCollectionService;

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("currentPostsColumnAlias","index");
        //置顶信息
        Page<Posts> topPostsPage = new com.baomidou.mybatisplus.plugins.Page<Posts>(
                1, 5);
        Wrapper<Posts> topPostsWrapper = new EntityWrapper<>();
        topPostsWrapper.eq("top",1);
        topPostsWrapper.eq("p.del_flag", DataBaseConstant.DEL_FLAG_NORMAL);
        topPostsWrapper.orderBy("publishTime",false);
        topPostsPage = postsService.selectPostsPage(topPostsPage,topPostsWrapper);
        List<Posts> topPostsList = topPostsPage.getRecords();
        model.addAttribute("topPostsList",topPostsList);
        //置顶信息
        Page<Posts> postsPage = new com.baomidou.mybatisplus.plugins.Page<Posts>(
                1, 10);
        Wrapper<Posts> postsWrapper = new EntityWrapper<>();
        //postsWrapper.eq("top",1);
        postsWrapper.eq("p.del_flag", DataBaseConstant.DEL_FLAG_NORMAL);
        postsWrapper.orderBy("publishTime",false);
        postsPage = postsService.selectPostsPage(postsPage,postsWrapper);
        List<Posts> postsList = postsPage.getRecords();
        model.addAttribute("postsList",postsList);
        return new ModelAndView("modules/front/posts/index");
    }
    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     */
    @GetMapping(value = "myPublistList")
    private Response myPublistList(@RequestParam("page") Integer page,
                              @RequestParam("limit") Integer limit) throws IOException {
        Response response = Response.ok();
        EntityWrapper<Posts> entityWrapper = new EntityWrapper<>(entityClass);
        entityWrapper.eq("p.del_flag",DataBaseConstant.DEL_FLAG_NORMAL);
        entityWrapper.eq("p.uid",UserUtils.getUser().getId());
        entityWrapper.orderBy("publishTime",false);
        Page<Posts> postsPageBean = new com.baomidou.mybatisplus.plugins.Page<Posts>(
                page, limit);
        postsPageBean = postsService.selectPostsPage(postsPageBean,entityWrapper);
        response.put("count",postsPageBean.getTotal());
        response.putList("data",postsPageBean.getRecords());
        return response;
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     */
    @GetMapping(value = "myCollectionList")
    private Response myCollectionList(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit) throws IOException {
        Response response = Response.ok();
        EntityWrapper<PostsCollection> entityWrapper = new EntityWrapper<PostsCollection>(PostsCollection.class);
        entityWrapper.eq("p.del_flag",DataBaseConstant.DEL_FLAG_NORMAL);
        entityWrapper.eq("c.collection_uid",UserUtils.getUser().getId());
        entityWrapper.orderBy("collectionTime",false);
        Page<PostsCollection> postsPageBean = new com.baomidou.mybatisplus.plugins.Page<PostsCollection>(
                1, 10);
        postsPageBean = postsCollectionService.selectCollectionPage(postsPageBean,entityWrapper);
        response.put("count",postsPageBean.getTotal());
        response.putList("data",postsPageBean.getRecords());
        return response;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!model.containsAttribute("data")) {
            model.addAttribute("data", newModel());
        }
        return new ModelAndView("modules/front/posts/edit");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        Posts posts = postsService.selectById(id);
        model.addAttribute("data", posts);
        return new ModelAndView("modules/front/posts/edit");
    }

    @PostMapping("add")
    public Response add(Posts entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("{id}/update")
    public Response update(Posts entity, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("/save")
    public Response doSave(Posts entity, HttpServletRequest request, HttpServletResponse response,
                                 BindingResult result) {
        //验证图形码
        if (!JCaptcha.validateResponse(request, request.getParameter("vercode"))){
            return Response.error(ResponseError.NORMAL_ERROR,"请输入正确的图形码");
        }
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
                entity.setPublishTime(new Date());
                entity.setUid(UserUtils.getUser().getId());
                entity.setDefault();
                postsService.insert(entity);
            } else {
                Posts oldPosts = postsService.selectById(entity.getId());
                BeanUtils.copyProperties(entity,oldPosts);
                postsService.insertOrUpdate(oldPosts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
        }
        Response responseData = Response.ok("保存成功");
        responseData.put("action","/posts/"+entity.getId()+"/detail");
        return responseData;
    }

    @GetMapping({"{alias}/page/{page}","{alias}","{alias}/{type}/page/{page}","{alias}/{type}"})
    public ModelAndView column(Model model,@PathVariable("alias") String alias,
                               @PathVariable(value = "type",required = false) String columnType,
                               @PathVariable(value = "page",required = false) Integer page,
                               @RequestParam(value = "orderBy",required = false,defaultValue = "publish") String orderBy) {
        String listUrl="/posts/"+alias;
        if (page == null){
            page = 1;
        }
        if (StringUtils.isEmpty(columnType)){
            columnType = "";
        }else{
            listUrl = listUrl+"/"+columnType;
        }
        Page postsPageBean = listPostsPage(page,20,alias,columnType,orderBy);
        model.addAttribute("postsPageBean",postsPageBean);
        model.addAttribute("alias",alias);
        model.addAttribute("currentPostsColumnAlias",alias);
        model.addAttribute("columnType",columnType);
        model.addAttribute("listUrl",listUrl);
        model.addAttribute("orderBy",orderBy);
        return new ModelAndView("modules/front/posts/column");
    }

    /**
     *  主题分页
     * @param current
     * @param size
     * @param alias
     * @return
     */
    private Page  listPostsPage(int current, int size,String alias,String columnType,String orderBy){
        //精华招考查询
        Page<Posts> pageBean = new com.baomidou.mybatisplus.plugins.Page<Posts>(
                current, size);
        EntityWrapper<Posts> entityWrapper=  new EntityWrapper<Posts>(Posts.class);
        //entityWrapper.setTableAlias("p");
        entityWrapper.eq("p.del_flag", DataBaseConstant.DEL_FLAG_NORMAL);
        if (!StringUtils.isEmpty(alias)&&!alias.equals("all")){
          PostsColumn postsColumn = PostsUtils.getPostsColumnByAlias(alias);
            entityWrapper.eq("p.column",postsColumn.getCode());
        }
        if (columnType.equals("unsolved")){ // 未结束
            entityWrapper.eq("p.solved",0);
        }else if (columnType.equals("solved")) { // 已结束
            entityWrapper.eq("p.solved",1);
        } else if (columnType.equals("essence")) { // 精华
            entityWrapper.eq("p.essence",1);
        }

        if (!StringUtils.isEmpty(orderBy)&&orderBy.equals("comment")) {
            entityWrapper.orderBy("commentCount", false);
        }else{
            entityWrapper.orderBy("publishTime", false);
        }
        pageBean = postsService.selectPostsPage(pageBean,entityWrapper);
        return pageBean;
    }

    @GetMapping({"{id}/detail","{id}/detail/page/{page}"})
    public ModelAndView detail(Model model,@PathVariable("id") String id,
                               @PathVariable(value = "page",required = false) Integer page) {
        if (page == null){
            page = 1;
        }
        Posts posts = postsService.findPostsById(id);
        if (posts.getDelFlag().equals(DataBaseConstant.DEL_FLAG_DELETE)){
            model.addAttribute("tips","该主题已被删除！");
            return new ModelAndView("modules/front/other/tips");
        }
        posts.setReadCount(posts.getReadCount()+1);
        postsService.insertOrUpdate(posts);
        model.addAttribute("posts", posts);
        //获得评论信息
        Page commentPageBean = listPostsComment(page,10,posts.getId(),"");
        model.addAttribute("commentPageBean",commentPageBean);
        model.addAttribute("listUrl","/posts/"+id+"/detail");
        return new ModelAndView("modules/front/posts/detail");
    }
    /**
     *  主题评论
     * @param current
     * @param size
     * @return
     */
    private Page  listPostsComment(int current, int size,String postsId, String orderBy){
        //精华招考查询
        Page<PostsComment> pageBean = new com.baomidou.mybatisplus.plugins.Page<PostsComment>(
                current, size);
        EntityWrapper<PostsComment> entityWrapper=  new EntityWrapper<PostsComment>(PostsComment.class);
        entityWrapper.setTableAlias("c");
        entityWrapper.eq("pid",postsId);
        entityWrapper.orderBy("publishTime", false);
        pageBean = postsCommentService.selectCommentPage(pageBean,entityWrapper,UserUtils.getUser().getId());
        return pageBean;
    }

    @PostMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        try {
            postsService.logicalDeleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"删除失败");
        }
        return Response.ok("删除成功");
    }

    @PostMapping("{id}/set")
    public Response set(@PathVariable("id") String id,@RequestParam("rank") Integer rank,@RequestParam("field") String field) {
        try {
            Posts posts= postsService.selectById(id);
            if (field.equals("top")){
                posts.setTop(rank);
            }else if (field.equals("essence")){
                posts.setEssence(rank);
            }
            postsService.insertOrUpdate(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"设置失败");
        }
        return Response.ok("设置成功");
    }


}