package cn.jeeweb.beetl.tags.form;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@BeetlTagName("form.radiobuttons")
public class RadioButtonsTag extends AbstractMultiCheckedElementTag {

	@Override
	protected String getInputType() {
		return "radio";
	}

}
