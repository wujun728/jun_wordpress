package com.mis.service;

import com.erp.service.IBaseService;
import com.mis.httpModel.EasyuiDataGrid;
import com.mis.httpModel.EasyuiDataGridJson;
import com.mis.httpModel.Portal;

/**
 * portal Service
 * 
 * @author 孙宇
 * 
 */
public interface PortalServiceI extends IBaseService {

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, Portal portal);

	public void del(String ids);

	public void edit(Portal portal);

	public void add(Portal portal);

}
