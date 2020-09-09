package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.front.constant.FrontConstant;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.entity.PostsCommentPraise;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentPraiseService;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IMessageService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.bbs.utils.HtmlMatchUtils;
import cn.jeeweb.bbs.utils.UserUtils;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.utils.ArrayUtils;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.bbs.controller
 * @title: 评论控制器
 * @description: 评论控制器
 * @author: 王存见
 * @date: 2018-08-29 17:51:13
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */

@RestController("FrontCommentController")
@RequestMapping("/posts/comment")
public class PostsCommentController extends BaseBeanController<PostsComment> {
    @Autowired
    private IPostsCommentPraiseService postsCommentPraiseService;
    @Autowired
    private IPostsCommentService postsCommentService;
    @Autowired
    private IPostsService postsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMessageService messageService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return display("list");
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })

    private void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        EntityWrapper<PostsComment> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<PostsComment> pagejson = new PageResponse<>(postsCommentService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @PostMapping("add")
    public Response add(PostsComment entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(entity, request, response, result);
    }

    @PostMapping("{id}/update")
    public Response update(PostsComment comment, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        return doSave(comment, request, response, result);
    }

    @PostMapping("/save")
    public Response doSave(PostsComment comment, HttpServletRequest request, HttpServletResponse response,
                           BindingResult result) {
        if (hasError(comment, result)) {
            // 错误提示
            String errorMsg = errorMsg(result);
            if (!StringUtils.isEmpty(errorMsg)) {
                return Response.error(ResponseError.NORMAL_ERROR,errorMsg);
            } else {
                return Response.error(ResponseError.NORMAL_ERROR,"保存失败");
            }
        }
        try {
            if (StringUtils.isEmpty(comment.getId())) {
                comment.setPublishTime(new Date());
                comment.setUid(UserUtils.getUser().getId());
                comment.setDefault();
                postsCommentService.insert(comment);
                sendCommentMessage(comment);
            } else {
                postsCommentService.insertOrUpdate(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"保存失败!<br />原因:" + e.getMessage());
        }
        Response responseData =   Response.ok("保存成功");
        responseData.put("action","/posts/"+comment.getPid()+"/detail");
        return responseData;
    }

    private void sendCommentMessage(PostsComment comment){
        // 设置通知信息,通知楼主
        Posts posts = postsService.findPostsById(comment.getPid());
        Map<String,Object> postsMap = new HashMap<>();
        postsMap.put("realname",posts.getUser().getRealname());
        postsMap.put("uid",posts.getUser().getId());
        postsMap.put("postsId",posts.getId());
        postsMap.put("postsTitle",posts.getTitle());
        messageService.sendMessage(posts.getUid(), FrontConstant.MESSAGE_COMMENT_REPLY_TEMPLATE_CODE,postsMap);
        // 设置通知AT的人
        List<String> atRealnameList = HtmlMatchUtils.findMatchAtRealName(comment.getContent());
        if (atRealnameList.size()>0){
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.setSqlSelect("id");
            userEntityWrapper.in("realname",atRealnameList);
            List<User> users = userService.selectList(userEntityWrapper);
            List<String> usersIdList = new ArrayList<>();
            for (User user: users) {
                usersIdList.add(user.getId());
            }
            if (usersIdList.size()>0) {
                messageService.sendMessage(usersIdList.toArray(new String[usersIdList.size()]), FrontConstant.MESSAGE_COMMENT_REPLY_AT_TEMPLATE_CODE, postsMap);
            }
        }
    }

    @PostMapping("{id}/accept")
    public Response accept(@PathVariable("id") String id) {
        Response response = Response.ok("采纳成功");
        try {
            postsCommentService.accept(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"采纳失败");
        }
        return response;
    }

    @PostMapping("{id}/praise")
    public Response praise(@PathVariable("id") String id) {
        Response response = Response.ok("赞成功");
        try {
            EntityWrapper<PostsCommentPraise> entityWrapper = new EntityWrapper<>(PostsCommentPraise.class);
            entityWrapper.eq("tid",id);
            entityWrapper.eq("praiseUid",UserUtils.getUser().getId());
            if (postsCommentPraiseService.selectCount(entityWrapper) == 0) {
                PostsCommentPraise praise = new PostsCommentPraise();
                praise.setPraiseTime(new Date());
                praise.setPraiseUid(UserUtils.getUser().getId());
                praise.setTid(id);
                postsCommentPraiseService.insert(praise);
            }else{
                postsCommentPraiseService.delete(entityWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"赞失败");
        }
        return response;
    }

    @PostMapping("{id}/get")
    public Response get(@PathVariable("id") String id) {
        Response response = Response.ok("获取成功");
        try {
            PostsComment comment = postsCommentService.selectById(id);
            response.putObject(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"获取失败");
        }
        return response;
    }

    @PostMapping("{id}/delete")
    public Response delete(@PathVariable("id") String id) {
        try {
            postsCommentService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(ResponseError.NORMAL_ERROR,"删除失败");
        }
        return Response.ok("删除成功");
    }


}