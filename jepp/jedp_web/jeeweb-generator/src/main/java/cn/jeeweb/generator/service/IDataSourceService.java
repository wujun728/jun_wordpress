package cn.jeeweb.generator.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.generator.common.dao.IDbHelper;
import cn.jeeweb.generator.entity.DataSource;

/**
 * @Title: 数据源
 * @Description: 数据源
 * @author jeeweb
 * @date 2017-05-10 12:01:57
 * @version V1.0   
 *
 */
public interface IDataSourceService extends ICommonService<DataSource> {
    /**
     * 获取Hepler
     * @param datasourid
     * @return
     */
    IDbHelper getDbHelper(String datasourid);

    void testConnect(DataSource dataSource);

}

