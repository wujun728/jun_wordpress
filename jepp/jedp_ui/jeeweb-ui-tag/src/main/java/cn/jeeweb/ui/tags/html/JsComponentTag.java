package cn.jeeweb.ui.tags.html;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@BeetlTagName("html.js")
public class JsComponentTag extends AbstractHtmlTag {
	private static final String[] SUPPORT_TYPES = { "JS" };

	@Override
	public String[] getSupportTypes() {
		return SUPPORT_TYPES;
	}

}
