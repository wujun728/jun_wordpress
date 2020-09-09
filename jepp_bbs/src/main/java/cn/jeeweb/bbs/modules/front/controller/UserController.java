package cn.jeeweb.bbs.modules.front.controller;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import cn.jeeweb.bbs.common.bean.ResponseError;
import cn.jeeweb.bbs.modules.email.service.IEmailSendService;
import cn.jeeweb.bbs.modules.front.constant.FrontConstant;
import cn.jeeweb.bbs.modules.posts.entity.PostsComment;
import cn.jeeweb.bbs.modules.posts.entity.Posts;
import cn.jeeweb.bbs.modules.posts.service.IPostsCommentService;
import cn.jeeweb.bbs.modules.posts.service.IPostsService;
import cn.jeeweb.bbs.modules.sys.entity.Message;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IMessageService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.bbs.security.shiro.credential.RetryLimitHashedCredentialsMatcher;
import cn.jeeweb.bbs.security.shiro.exception.RepeatAuthenticationException;
import cn.jeeweb.bbs.security.shiro.filter.authc.FormAuthenticationFilter;
import cn.jeeweb.bbs.security.shiro.filter.authc.UsernamePasswordToken;
import cn.jeeweb.bbs.utils.LoginLogUtils;
import cn.jeeweb.bbs.utils.SmsVercode;
import cn.jeeweb.bbs.utils.UrlUtils;
import cn.jeeweb.bbs.utils.UserUtils;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.*;
import cn.jeeweb.common.utils.jcaptcha.JCaptcha;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController("FrontUserController")
@RequestMapping("user")
public class UserController extends BaseController {
	@Autowired
	private RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPostsService postsService;
	@Autowired
	private IPostsCommentService postsCommentService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IEmailSendService emailSendService;

	/**
	 *  登陆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public Object login(HttpServletRequest request, HttpServletRequest response, Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			//如果用户没有登录，跳转到登陆页面
			if (!subject.isAuthenticated()) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				//验证验证码
				if (request.getMethod().equalsIgnoreCase("post")) {
					//验证图形码
					if (!JCaptcha.validateResponse(request, request.getParameter("captcha"))){
						LoginLogUtils.recordFailLoginLog(username,"请输入正确的图形码");
						return Response.error(ResponseError.NORMAL_ERROR,"请输入正确的图形码");
					}
				}
				if (request.getMethod().equalsIgnoreCase("get") || !login(subject, username, password, "", request)) {//登录失败时跳转到登陆页面
					String useruame = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
					String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
					if (request.getMethod().equalsIgnoreCase("post")){
						LoginLogUtils.recordFailLoginLog(username,"帐号或密码错误，登录失败");
						return Response.error("帐号或密码错误，登录失败！");
					}
					// 强制登陆跳转
					if (ExcessiveAttemptsException.class.getName().equals(exception)
							|| retryLimitHashedCredentialsMatcher.isForceLogin(useruame)) { // 重复认证异常加入验证码。
					}
					if (!ServletUtils.isAjax()) {
						return new ModelAndView("modules/front/user/login");
					}else {
						return Response.error("还未登录，请登录!");
					}
				}
				Response responseData = Response.ok();
				LoginLogUtils.recordSuccessLoginLog(username,"登陆成功");
				responseData.put("action", UrlUtils.getRefererUrl());
				return responseData;
			}else{
				return new ModelAndView("redirect:" + UrlUtils.getRefererUrl());
			}
		}
		catch (Exception e) {
		  return Response.error(e.getMessage());
		}
	}

	/**
	 * 登陆方法
	 *
	 * @param subject
	 * @param request
	 * @return
	 */
	private boolean login(Subject subject,String username,String password,String captcha,HttpServletRequest request) {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		boolean rememberMe = true;
		String host = IpUtils.getIpAddr((HttpServletRequest) request);
		UsernamePasswordToken token= new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha,false);
		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}
	/**
	 * 获取验证码
	 *
	 * @param phone 手机号码
	 * @param type 分类 register
	 * @return
	 */
	@GetMapping("{type}/vercode")
	public Response verCode(@RequestParam String phone, @PathVariable String type) {
		try {
			//发送验证码，这里采用encache缓存

		}catch (Exception e){

		}
		return Response.ok();
	}
	/**
	 * 用户注册页面
	 *
	 * @return
	 */
	@GetMapping("/register")
	public ModelAndView showRegister(Model model) {
		CookieUtils.setCookie(ServletUtils.getResponse(),"vercode_type","register");
		return new ModelAndView("modules/front/user/register");
	}

	/**
	 * 用户注册页面
	 *
	 * @return
	 */
	@PostMapping("/register")
	public Object doRegister(@RequestParam("vercode") String vercode,
								   @RequestParam("phone") String phone,
								   @RequestParam("realname") String realname,
								   @RequestParam("pass") String pass,
								   @RequestParam("repass") String repass) {
		try {
			//验证手机验证码
			if (!SmsVercode.validateCode(ServletUtils.getRequest(),phone,vercode)){
				return Response.error("手机验证码错误！");
			}
			if (!pass.equals(repass)){
				return Response.error("两次输入的密码不相同！");
			}
			//手机号码
			if (userService.findByPhone(phone)!=null){
				return Response.error("您的手机号码已经存在，请直接登陆！");
			}
			//昵称
			if (userService.findByRealname(realname)!=null){
				return Response.error("您的昵称已经被使用，请更换！");
			}
			User user = new User();
			user.setDefault();
			user.setPhone(phone);
			user.setRealname(realname);
			user.setPassword(pass);
			Random random = new Random();
			String portrait = "/static/images/avatar/"+random.nextInt(13)+".jpg";
			user.setPortrait(portrait);
			user.setCity(AddressUtils.getRealAddressByIP(IpUtils.getIpAddr(ServletUtils.getRequest())));
			userService.register(user);
			Response response = Response.ok("恭喜您注册成功，请登录！");
			response.put("action","/user/login");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(e.getMessage());
		}
	}

	/**
	 * 用户注册页面
	 *
	 * @return
	 */
	@GetMapping("/forget")
	public ModelAndView showForget(Model model,@RequestParam(value = "type",required = false) String type) {
		if (StringUtils.isEmpty(type)){
			type = "phone";
			CookieUtils.setCookie(ServletUtils.getResponse(),"vercode_type","forget");
		}
		model.addAttribute("type",type);
		String token = ServletUtils.getRequest().getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			User user = (User) CacheUtils.get(token);
			model.addAttribute("token", token);
			model.addAttribute("user", user);
			String key = ServletUtils.getRequest().getParameter("key");
			String value = ServletUtils.getRequest().getParameter("value");
			model.addAttribute("key", key);
			model.addAttribute("value", value);
		}
		return new ModelAndView("modules/front/user/forget");
	}

	/**
	 * 用户注册页面
	 *
	 * @return
	 */
	@PostMapping("/forget")
	public Object doForget(
			@RequestParam(value = "type",required = false) String type,
			@RequestParam(value = "vercode",required = false) String vercode,
			@RequestParam(value = "imagecode", required = false) String imagecode,
			@RequestParam(value = "email",required = false) String email,
			@RequestParam(value = "phone",required = false) String phone,
			HttpServletRequest request) {
		try {
			//验证手机验证码
			User user = null;
			if (StringUtils.isEmpty(type)) {
				type = "phone";
			}
			if (type.equals("phone")) {
				if (!SmsVercode.validateCode(ServletUtils.getRequest(), phone, vercode)) {
					return Response.error("手机验证码错误！");
				}
				//手机号码
				user = userService.findByPhone(phone);
				if (user == null) {
					return Response.error("未找到手机号码，请确认手机号码！");
				}
			}else{
				if (!JCaptcha.validateResponse(request, imagecode)) {
					return Response.error(ResponseError.NORMAL_ERROR, "请输入正确的图形码");
				}
				//邮件
				user = userService.findByEmail(email);
				if (user == null) {
					return Response.error("未找到该邮箱的账号，请确认邮箱号码！");
				}
			}
			String token = StringUtils.randomUUID();
			CacheUtils.put(token,user);
			if (type.equals("phone")) {
				Response response = Response.ok("校验成功，请点击确认完成密码修改操作。");
				response.put("action", "/user/reset?token=" + token + "&key=" + type + "&value=" + phone);
				return response;
			}else{
				Response response = Response.ok("已将密码重置地址发送至您的邮箱，请注意查收");
				response.put("action", "/user/forget?type=email");
				sendForgetEmail(user,token);
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(e.getMessage());
		}
	}

	/**
	 * 设置密码
	 *
	 * @return
	 */
	@GetMapping("/reset")
	public ModelAndView showResetPassword(Model model,
										  @RequestParam(value = "token") String token,
										  @RequestParam(value = "key") String key,
										  @RequestParam(value = "value") String value) {
		model.addAttribute("showError", 1);
		if (!StringUtils.isEmpty(token)) {
			User user = (User) CacheUtils.get(token);
			if (user != null){
				model.addAttribute("showError", 0);
			}
			model.addAttribute("token", token);
			model.addAttribute("user", user);
			model.addAttribute("key", key);
			model.addAttribute("value", value);
		}
		return new ModelAndView("modules/front/user/reset");
	}

	/**
	 * 设置密码
	 *
	 * @return
	 */
	@PostMapping("/reset")
	public Object doResetPassword(@RequestParam(value = "token") String token,
								  @RequestParam("imagecode") String imagecode,
								  @RequestParam("pass") String pass,
								  @RequestParam("repass") String repass,
								  HttpServletRequest request) {
		try {
			User user = (User) CacheUtils.get(token);
			//验证图形码
			if (user == null) {
				return Response.error(ResponseError.NORMAL_ERROR, "非法链接，请重新校验您的信息");
			}
			//验证图形码
			if (!JCaptcha.validateResponse(request, imagecode)) {
				return Response.error(ResponseError.NORMAL_ERROR, "请输入正确的图形码");
			}
			if (!pass.equals(repass)){
				return Response.error("两次输入的密码不相同！");
			}
			//手机号码
			userService.changePassword(user.getId(), pass);
			CacheUtils.remove(token);
			Response response = Response.ok("密码重置成功。");
			response.put("action","/user/login");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(e.getMessage());
		}
	}
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout() {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				LoginLogUtils.recordLogoutLoginLog(UserUtils.getUser().getUsername(),"退出成功");
				subject.logout();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:"+UrlUtils.getRefererUrl());
	}


	/**
	 * 用户中心
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletRequest response, Model model) {
		model.addAttribute("currentUserMenu","index");
		User showUser = userService.selectById(UserUtils.getUser().getId());
		model.addAttribute("showUser",showUser);
		return new ModelAndView("modules/front/user/index");
	}

	/**
	 * 设置
	 * @return
	 */
	@RequestMapping("/set")
	public ModelAndView set(HttpServletRequest request, HttpServletRequest response, Model model) {
		model.addAttribute("currentUserMenu","set");
		return new ModelAndView("modules/front/user/set");
	}

	/**
	 * 保存基础信息
	 * @param user
	 * @return
	 */
	@PostMapping(value = "{id}/saveInfo")
	public Response saveInfo(User user) {
		try {
			User oldUser = userService.selectById(user.getId());
			// 判断邮箱是否已经使用
			User emailUser = userService.findByEmail(user.getEmail());
			if (emailUser!=null&&!emailUser.getId().equals(user.getId())){
				return Response.error("该邮箱已被使用，请更换其他邮箱！");
			}
			// 判断邮箱是否需要重新激活
			if (!user.getEmail().equals(oldUser.getEmail())){
				// 需要激活
				user.setEmailActivate("0");
				// 发送邮件
				sendActivateEmail(user);
			}
			BeanUtils.copyProperties(user,oldUser);
			userService.insertOrUpdate(oldUser);
			String currentUserId = UserUtils.getUser().getId();
			if (currentUserId.equals(user.getId())) {
				UserUtils.clearCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.error("信息保存失败");
		}
		Response response =Response.ok("信息保存成功");
		response.put("action","/user/set#info");
		return response;
	}

	/**
	 * 发送找回密码
	 * @param user
	 */
	private void sendForgetEmail(User user,String token){
		// 发送邮件
		Map<String,Object> datas = Maps.newHashMap();
		datas.put("realname",user.getRealname());
		CacheUtils.put(token,user);
		datas.put("email",user.getEmail());
		datas.put("token",token);
		emailSendService.send(user.getEmail(), FrontConstant.EMAIL_FORGET_PASS_TEMPLATE_CODE,datas);
	}

	/**
	 * 发送激活邮件
	 * @param user
	 */
	private void sendActivateEmail(User user){
		// 发送邮件
		Map<String,Object> datas = Maps.newHashMap();
		datas.put("realname",user.getRealname());
		String token = StringUtils.randomUUID();
		CacheUtils.put(token,user);
		datas.put("token",token);
		emailSendService.send(user.getEmail(), FrontConstant.EMAIL_ACTIVATE_EMAIL_TEMPLATE_CODE,datas);
	}
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 */
	@PostMapping(value = "{id}/changePassword")
	public Response changePassword(@PathVariable("id") String id,
								   @RequestParam("nowPassword") String nowPassword,
								   @RequestParam("password") String password,
								   @RequestParam("rePassword") String rePassword) {
		if (userService.checkPassword(id,nowPassword)){
			if (!password.equals(rePassword)){
				return Response.error("两次输入的密码不相同！");
			}
			userService.changePassword(id, password);
			try {
				Subject subject = SecurityUtils.getSubject();
				if (subject != null && subject.isAuthenticated()) {
					subject.logout();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return Response.error("当前密码不正确！");
		}
		Response response =Response.ok("密码修改成功");
		response.put("action","/user/login");
		return response;
	}

	/**
	 * 修改头像
	 * @param id
	 * @param avatar
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = {"{id}/avatar","avatar"})
	public Response avatar(@PathVariable(value = "id",required = false) String id,@RequestParam("avatar") String avatar, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(id)){
				id = UserUtils.getUser().getId();
			}
			User user = userService.selectById(id);
			user.setPortrait(avatar);
			userService.insertOrUpdate(user);
			String currentUserId = UserUtils.getUser().getId();
			if (currentUserId.equals(user.getId())) {
				UserUtils.clearCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.error("头像修改失败");
		}
		return Response.ok("头像修改成功");
	}

	/**
	 *  我的帖子
	 * @return
	 */
	@RequestMapping("/posts")
	public ModelAndView posts(HttpServletRequest request, HttpServletRequest response, Model model) {
		model.addAttribute("currentUserMenu","posts");
		//帖子查询
		EntityWrapper<Posts> postsEntityWrapper=  new EntityWrapper<>(Posts.class);
		postsEntityWrapper.setTableAlias("p");
		postsEntityWrapper.eq("uid",UserUtils.getUser().getId());
		postsEntityWrapper.orderBy("publishTime", false);
		Page<Posts> postsPageBean = new com.baomidou.mybatisplus.plugins.Page<Posts>(
				1, 10);
		postsPageBean = postsService.selectPostsPage(postsPageBean,postsEntityWrapper);
		model.addAttribute("postsPageBean",postsPageBean);
		return new ModelAndView("modules/front/user/posts");
	}

	/**
	 *  我的消息
	 * @return
	 */
	@RequestMapping("/message")
	public ModelAndView message(HttpServletRequest request, HttpServletRequest response, Model model) {
		model.addAttribute("currentUserMenu","message");
		EntityWrapper<Message> entityWrapper = new EntityWrapper<Message>(Message.class);
		entityWrapper.eq("read",0);
		entityWrapper.eq("readUid",UserUtils.getUser().getId());
		entityWrapper.orderBy("sendDate",false);
		List<Message> messageList = messageService.selectList(entityWrapper);
		model.addAttribute("messageCount",messageList.size());
		model.addAttribute("messageList",messageList);
		return new ModelAndView("modules/front/user/message");
	}

	/**
	 *  邮箱激活
	 * @return
	 */
	@GetMapping("/activate")
	public ModelAndView activate(Model model,
								 @RequestParam(value = "token",required = false) String token) {
        //激活代码
		if (!StringUtils.isEmpty(token)){
			User user = (User)CacheUtils.get(token);
			if (user!=null){
				user = userService.selectById(user.getId());
				user.setEmailActivate("1");
				userService.insertOrUpdate(user);
				model.addAttribute("user",user);
				CacheUtils.remove(token);
				return new ModelAndView("modules/front/user/activate");
			}else{
				model.addAttribute("tips","非法链接，请重新校验您的信息");
				return new ModelAndView("modules/front/other/tips");
			}
		}else{
			User user = userService.selectById(UserUtils.getUser().getId());
			model.addAttribute("user",user);
		}
		return new ModelAndView("modules/front/user/activate");
	}

	/**
	 *  邮箱激活
	 * @return
	 */
	@PostMapping("/activate")
	public Response activate() {
		User user = userService.selectById(UserUtils.getUser().getId());
		sendActivateEmail(user);
		return Response.ok("已成功将激活链接发送到了您的邮箱，接受可能会稍有延迟，请注意查收。");
	}

	/**
	 *  找回密码
	 * @return
	 */
	@RequestMapping("/forget")
	public ModelAndView forget() {
		return new ModelAndView("modules/front/user/forget");
	}

	/**
	 *  已购产品
	 *
	 * @return
	 */
	@RequestMapping("/product")
	public ModelAndView product(Model model,
								@RequestParam(value = "alias",required = false) String alias) {
		model.addAttribute("currentUserMenu","product");
		return new ModelAndView("modules/front/user/product");
	}

	/**
	 *  找回密码
	 * @return
	 */
	@RequestMapping("/jump")
	public ModelAndView forget(@RequestParam("realname") String realname) {
		EntityWrapper entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("realname",realname);
		User user = userService.selectOne(entityWrapper);
		if (user!=null){
			String redirectUrl = "redirect:/user/home/"+user.getId();
			return new ModelAndView(redirectUrl);
		}
		return new ModelAndView("redirect:/404");
	}

	/**
	 * 我的主页
	 * @return
	 */
	@RequestMapping(value = {"/home/{uid}","/home"})
	public ModelAndView home(@PathVariable(value = "uid",required = false) String uid, HttpServletRequest request, HttpServletRequest response, Model model) {
		model.addAttribute("currentUserMenu","home");
		User showUser = null;
		if (StringUtils.isEmpty(uid)){
			uid = UserUtils.getUser().getId();
			showUser = userService.selectById(uid);
		}else{
			showUser = userService.selectById(uid);
		}
		model.addAttribute("showUser",showUser);
		model.addAttribute("currentUserMenu","home");
		//帖子查询
		EntityWrapper<Posts> postsEntityWrapper=  new EntityWrapper<>(Posts.class);
		postsEntityWrapper.setTableAlias("p");
		postsEntityWrapper.eq("uid",uid);
		postsEntityWrapper.orderBy("publishTime", false);
		Page<Posts> postsPageBean = new com.baomidou.mybatisplus.plugins.Page<Posts>(
				1, 10);
		postsPageBean = postsService.selectPostsPage(postsPageBean,postsEntityWrapper);
		model.addAttribute("postsPageBean",postsPageBean);
		//帖子评论
		Page<PostsComment> commentPageBean = new com.baomidou.mybatisplus.plugins.Page<PostsComment>(
				1, 5);
		EntityWrapper<PostsComment> commentEntityWrapper=  new EntityWrapper<PostsComment>(PostsComment.class);
		commentEntityWrapper.setTableAlias("c");
		commentEntityWrapper.eq("uid",uid);
		commentEntityWrapper.orderBy("publishTime", false);
		commentPageBean = postsCommentService.selectCommentPage(commentPageBean,commentEntityWrapper,UserUtils.getUser().getId());
		model.addAttribute("commentPageBean",commentPageBean);

		return new ModelAndView("modules/front/user/home");
	}

}
