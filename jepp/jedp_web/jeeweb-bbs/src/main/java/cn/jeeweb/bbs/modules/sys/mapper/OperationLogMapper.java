package cn.jeeweb.bbs.modules.sys.mapper;

import cn.jeeweb.bbs.modules.sys.entity.OperationLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

 /**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.sys.mapper
 * @title: 操作日志数据库控制层接口
 * @description: 操作日志数据库控制层接口
 * @author: 王存见
 * @date: 2018-09-30 15:53:25
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    
}