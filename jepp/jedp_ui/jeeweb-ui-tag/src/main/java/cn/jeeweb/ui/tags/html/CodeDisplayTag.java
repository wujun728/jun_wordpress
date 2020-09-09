package cn.jeeweb.ui.tags.html;

import java.util.HashMap;
import java.util.Map;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import cn.jeeweb.ui.tags.form.support.FreemarkerFormTagHelper;
import cn.jeeweb.ui.tags.html.manager.HtmlComponentManager;
import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@BeetlTagName("html.codedisplay")
public class CodeDisplayTag extends DisplayTag {
	// 代码类型
	private String codeType = "java";

	@Override
	protected String getContent() {
		String bodyContent = super.getContent();
		Map<String, Object> rootMap = FreemarkerFormTagHelper.getTagStatic(this, this.ctx);
		rootMap.put("codeType", codeType.toLowerCase());
		rootMap.put("bodyContent", bodyContent);
		HtmlComponentManager htmlComponentManager = SpringContextHolder.getApplicationContext()
				.getBean(HtmlComponentManager.class);
		String fragment = htmlComponentManager.getFragmentComponent("syntaxhighlighter-code", rootMap);
		if (!StringUtils.isEmpty(fragment) && !fragment.equals("null")) {
			// 获得编辑器
			return fragment;
		}
		return bodyContent;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}