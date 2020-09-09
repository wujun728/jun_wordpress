package cn.jeeweb.ui.tags.html;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@BeetlTagName("html.css")
public class CssComponentTag extends AbstractHtmlTag {
	private static final String[] SUPPORT_TYPES = { "CSS" };

	@Override
	public String[] getSupportTypes() {
		return SUPPORT_TYPES;
	}

}
