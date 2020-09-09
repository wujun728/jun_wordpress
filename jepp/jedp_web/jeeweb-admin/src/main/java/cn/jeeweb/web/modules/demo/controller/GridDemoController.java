package cn.jeeweb.web.modules.demo.controller;


import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jeeweb.common.mvc.controller.BaseController;

/**
 * 
 * All rights Reserved, Designed By www.jeeweb.cn
 * 
 * @title: GridDemoController.java
 * @package cn.jeeweb.web.modules.demo.controller
 * @description: GridDemo
 * @author: 王存见
 * @date: 2017年5月18日 下午6:17:24
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */

@Controller
@RequestMapping("${jeeweb.admin.url.prefix}/demo/grid")
@ViewPrefix("modules/demo/grid")
public class GridDemoController extends BaseController {

	/**
	 * 
	 * @title: list
	 * @description: 列表
	 * @return
	 * @return: String
	 */
	@RequestMapping("/list")
	public String list() {
		return display("list");
	}
	/**
	 * 
	 * @title: list
	 * @description: 列表
	 * @return
	 * @return: String
	 */
	@RequestMapping("/list_inline")
	public String listLline() {
		return display("list-inline");
	}

	 

}
