package cn.jeeweb.beetl.tags;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import cn.jeeweb.beetl.tags.exception.BeetlTagException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class HtmlEscapeTag extends RequestContextAwareTag {

	private boolean defaultHtmlEscape;


	/**
	 * Set the default value for HTML escaping,
	 * to be put into the current PageContext.
	 */
	public void setDefaultHtmlEscape(boolean defaultHtmlEscape) {
		this.defaultHtmlEscape = defaultHtmlEscape;
	}


	@Override
	protected int doStartTagInternal() throws BeetlTagException {
		getRequestContext().setDefaultHtmlEscape(this.defaultHtmlEscape);
		return EVAL_BODY_INCLUDE;
	}

}
