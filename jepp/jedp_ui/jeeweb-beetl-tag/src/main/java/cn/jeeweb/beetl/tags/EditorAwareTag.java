package cn.jeeweb.beetl.tags;

import java.beans.PropertyEditor;
import cn.jeeweb.beetl.tags.exception.BeetlTagException;
import org.springframework.lang.Nullable;

public interface EditorAwareTag {

	/**
	 * Retrieve the PropertyEditor for the property that this tag is
	 * currently bound to. Intended for cooperating nesting tags.
	 * @return the current PropertyEditor, or {@code null} if none
	 * @throws BeetlTagException if resolving the editor failed
	 */
	@Nullable
	PropertyEditor getEditor() throws BeetlTagException;

}
