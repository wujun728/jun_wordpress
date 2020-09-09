package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.front.constant.FrontConstant;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.bbs.utils.SmsVercode;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.utils.CookieUtils;
import cn.jeeweb.common.utils.jcaptcha.JCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
@RestController("FrontAuthController")
@RequestMapping("auth")
public class AuthController extends BaseController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/phone/code")
    public Response phone(@RequestParam("phone") String phone,
                           @RequestParam("imagecode") String imagecode,
                          HttpServletRequest request) {
        //验证图形码
        if (!JCaptcha.validateResponse(request, imagecode)) {
            return Response.error(ResponseError.NORMAL_ERROR, "请输入正确的图形码");
        }
        String vercodeType = CookieUtils.getCookie(request,"vercode_type");
        if (vercodeType.equals("register")){
            //判断手机号码是否已经注册
            if (userService.findByPhone(phone)!=null){
                return Response.error(ResponseError.NORMAL_ERROR, "手机号码已经注册，请直接登陆");
            }
            SmsVercode.generateCode(request,phone, FrontConstant.SMS_REGISTER_TEMPLATE_CODE);
        }else if (vercodeType.equals("forget")){
            //判断手机号码是否已经注册
            if (userService.findByPhone(phone)==null){
                return Response.error(ResponseError.NORMAL_ERROR, "未找到手机号码，请确认手机号码");
            }
            SmsVercode.generateCode(request,phone,FrontConstant.SMS_FORGET_PASS_TEMPLATE_CODE);
        }
        return Response.ok();
    }






}