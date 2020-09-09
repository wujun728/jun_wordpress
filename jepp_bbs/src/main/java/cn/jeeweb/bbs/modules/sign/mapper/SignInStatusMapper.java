package cn.jeeweb.bbs.modules.sign.mapper;

import cn.jeeweb.bbs.modules.sign.entity.SignInStatus;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.sign.mapper
 * @title: 登陆状态数据库控制层接口
 * @description: 登陆状态状态数据库控制层接口
 * @author: 王存见
 * @date: 2018-09-05 18:45:25
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface SignInStatusMapper extends BaseMapper<SignInStatus> {
 List<SignInStatus> selectLatestSignList(Pagination page);
}