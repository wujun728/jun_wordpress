package cn.jeeweb.bbs.modules.front.controller;

import cn.jeeweb.common.mvc.controller.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("FrontDocController")
@RequestMapping("doc")
public class DocController extends BaseController {

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView index(Model model, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("modules/front/doc/index");
	}
}
