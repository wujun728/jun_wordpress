package cn.jeeweb.beetl.tags.form;

import java.beans.PropertyEditor;
import cn.jeeweb.beetl.tags.exception.BeetlTagException;
import org.springframework.lang.Nullable;
import cn.jeeweb.beetl.tags.HtmlEscapingAwareTag;

public abstract class AbstractFormTag extends HtmlEscapingAwareTag {


	protected Object evaluate(String attributeName, Object value) throws BeetlTagException {
		return value;
	}


	protected final void writeOptionalAttribute(TagWriter tagWriter, String attributeName, @Nullable String value)
			throws BeetlTagException {

		if (value != null) {
			tagWriter.writeOptionalAttributeValue(attributeName, getDisplayString(evaluate(attributeName, value)));
		}
	}


	protected  TagWriter createTagWriter() {
		return new TagWriter(this.ctx);
	}

	@Override
	protected final int doStartTagInternal() throws BeetlTagException {
		return writeTagContent(createTagWriter());
	}

	protected String getDisplayString(Object value) {
		return ValueFormatter.getDisplayString(value, isHtmlEscape());
	}

	protected String getDisplayString(Object value, PropertyEditor propertyEditor) {
		return ValueFormatter.getDisplayString(value, propertyEditor, isHtmlEscape());
	}

	protected abstract int writeTagContent(TagWriter tagWriter) throws BeetlTagException;

}
