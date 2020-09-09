package cn.jeeweb.generator.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.generator.common.dao.DbHelper;
import cn.jeeweb.generator.common.dao.IDbHelper;
import cn.jeeweb.generator.entity.DataSource;
import cn.jeeweb.generator.mapper.DataSourceMapper;
import cn.jeeweb.generator.service.IDataSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**   
 * @Title: 数据源
 * @Description: 数据源
 * @author jeeweb
 * @date 2017-05-10 12:01:57
 * @version V1.0   
 *
 */
@Transactional
@Service("dataSourceService")
public class DataSourceServiceImpl  extends CommonServiceImpl<DataSourceMapper,DataSource> implements IDataSourceService {
    public IDbHelper getDbHelper(String datasourid){
        DataSource dataSource=selectById(datasourid);
        Connection connection=getConnection(dataSource);
        return new DbHelper(connection,dataSource.getDbType(),dataSource.getDbName());
    }

    public Connection getConnection(DataSource dataSource){
        try {
            Connection conn = null;
            String dbType = dataSource.getDbType();
            String url = dataSource.getUrl();
            String username = dataSource.getDbUser();
            String password = dataSource.getDbPassword();
            String driverClassName = dataSource.getDriverClass();
            Properties props = new Properties();
            if (username != null) {
                props.put("user", username);
            }
            if (password != null) {
                props.put("password", password);
            }
            if (dbType.equals("oracle")) {
                props.put("remarksReporting", "true");
            }
            // 初始化JDBC驱动并让驱动加载到jvm中
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, props);
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void testConnect(DataSource dataSource) {
         getConnection(dataSource);
    }
}
