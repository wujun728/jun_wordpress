package cn.jeeweb.bbs.modules.example.mapper;

import cn.jeeweb.bbs.modules.example.entity.Example;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.example.mapper
 * @title: example数据库控制层接口
 * @description: example数据库控制层接口
 * @author: 王存见
 * @date: 2018-09-04 16:46:10
 * @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
 */
@Mapper
public interface ExampleMapper extends BaseMapper<Example> {
  /**
   *
   * @title: selectUserList
   * @description: 查找主题列表
   * @param wrapper
   * @return
   * @return: List<User>
   */
  List<Example> selectExampleList(Pagination page, @Param("ew") Wrapper<Example> wrapper, @Param("userId") String userId);
}