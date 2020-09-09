package cn.jeeweb.generator.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.generator.entity.Column;
import java.util.List;

public interface IColumnService extends ICommonService<Column> {
	/**
	 * 通过表ID获得所有的列
	 * @param tableId
	 * @return
	 */
	List<Column> selectListByTableId(String tableId);

}
