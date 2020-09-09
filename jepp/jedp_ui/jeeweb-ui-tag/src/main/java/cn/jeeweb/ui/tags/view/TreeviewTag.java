package cn.jeeweb.ui.tags.view;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import cn.jeeweb.beetl.tags.exception.BeetlTagException;
import cn.jeeweb.common.utils.fastjson.FastjsonPropertyPreFilter;
import cn.jeeweb.common.utils.fastjson.FastjsonUnXssFilter;
import cn.jeeweb.ui.tags.tag.AbstractGridHtmlTag;
import cn.jeeweb.ui.tags.form.support.FreemarkerFormTagHelper;
import cn.jeeweb.ui.tags.html.manager.HtmlComponentManager;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.common.utils.Reflections;
import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@BeetlTagName("view.treeview")
public class TreeviewTag extends AbstractGridHtmlTag {
	private String id = "treeview"; // 树唯一标示
	private String dataUrl = "";// 访问路径
	private Object datas;// 设置的数据
	private String onNodeSelected = "";// 事件
	protected String treeviewSettingCallback = ""; // 配置方法,为js方法，返回配置

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

	public String getOnNodeSelected() {
		return onNodeSelected;
	}

	public void setOnNodeSelected(String onNodeSelected) {
		this.onNodeSelected = onNodeSelected;
	}

	public String getTreeviewSettingCallback() {
		return treeviewSettingCallback;
	}

	public void setTreeviewSettingCallback(String treeviewSettingCallback) {
		this.treeviewSettingCallback = treeviewSettingCallback;
	}

	@Override
	public int doStartTag() throws BeetlTagException {
		// 清空资源
		if (staticAttributes != null) {
			staticAttributes.clear();
		}
		Field[] field = getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			Field field2 = field[j];
			if (ObjectUtils.isBaseDataType(field2.getType())) {
				String name = field[j].getName(); // 获取属性的名字
				setStaticAttribute(name, Reflections.invokeGetter(this, name));
			}
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws BeetlTagException {
		try {
			writeFragment();
		} catch (BeetlTagException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	private void writeFragment() throws BeetlTagException {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		if (datas != null) {
			String initDatas = "";
			List dataList = (List) datas;
			if (dataList != null && dataList.size() > 0) {
				Class<?> clazz = dataList.get(0).getClass();
				FastjsonPropertyPreFilter fastjsonPropertyPreFilter = new FastjsonPropertyPreFilter(clazz, "text,href,tags,nodes");
				FastjsonUnXssFilter fastjsonUnXssFilter = new FastjsonUnXssFilter();
				SerializeFilter[] filters = {fastjsonPropertyPreFilter, fastjsonUnXssFilter};
				initDatas  = JSON.toJSONString(datas, filters);
			}
			if (StringUtils.isEmpty(initDatas)) {
				initDatas = "[]";
			}
			rootMap.put("initDatas", initDatas);
		}
		Map<String, Object> staticMap = FreemarkerFormTagHelper.getTagStatic(this, this.ctx);
		rootMap.putAll(staticMap);
		HtmlComponentManager htmlComponentManager = SpringContextHolder.getApplicationContext()
				.getBean(HtmlComponentManager.class);
		String fragment = htmlComponentManager.getFragmentComponent("bootstrap-treeview", rootMap);
		if (!StringUtils.isEmpty(fragment) && !fragment.equals("null")) {
			// 获得编辑器
			try {
				this.ctx.byteWriter.writeString(fragment);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
