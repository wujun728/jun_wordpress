package cn.jeeweb.web.modules.sys.mapper;


import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	/**
	 * 
	 * @title: findRoleByUserId   
	 * @description: 通过用户查找角色
	 * @param userId
	 * @return      
	 * @return: List<Role>
	 */
	List<Role> findRoleByUserId(String userId);
}
