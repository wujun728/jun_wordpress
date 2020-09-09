package cn.jeeweb.bbs.modules.sys.service.impl;

import java.io.Serializable;
import java.util.Collection;

import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.entity.UserRole;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.bbs.modules.sys.entity.UserOrganization;
import cn.jeeweb.bbs.modules.sys.mapper.UserMapper;
import cn.jeeweb.bbs.modules.sys.service.IUserOrganizationService;
import cn.jeeweb.bbs.modules.sys.service.IUserRoleService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

@Transactional
@Service("userService")
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements IUserService {
	@Autowired
	PasswordService passwordService;
	@Autowired
	private IUserOrganizationService userOrganizationService;
	@Autowired
	private IUserRoleService userRoleService;

	@Override
	public Boolean checkPassword(String userId, String nowPassword) {
		User user = selectById(userId);
		if (user != null) {
			String oldPassword=user.getPassword();
			user.setPassword(nowPassword);
			passwordService.chenckEncryptPassword(user);
			String newPassword= user.getPassword();
			if (oldPassword.equals(newPassword)){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public void changePassword(String userId, String newPassword) {
		User user = selectById(userId);
		if (user != null) {
			user.setPassword(newPassword);
			passwordService.encryptPassword(user);
		}
		insertOrUpdate(user);
	}

	@Override
	public User findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("username", username));
	}

	@Override
	public User findByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("email", email));
	}

	@Override
	public User findByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("phone", phone));
	}

	@Override
	public User findByRealname(String realname) {
		if (StringUtils.isEmpty(realname)) {
			return null;
		}
		return selectOne(new EntityWrapper<User>(User.class).eq("realname", realname));
	}

	@Override
	public boolean insert(User user) {
		//设置默认值
		user.setDefault();
		passwordService.encryptPassword(user);
		return super.insert(user);
	}

	@Override
	public boolean deleteById(Serializable id) {
		// 删除角色关联
		userRoleService.delete(new EntityWrapper<UserRole>(UserRole.class).eq("userId", id));
		// 删除部门关联
		userOrganizationService.delete(new EntityWrapper<UserOrganization>(UserOrganization.class).eq("userId", id));
		return super.deleteById(id);
	}

	@Override
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		for (Object id : idList) {
			this.deleteById((Serializable) id);
		}
		return true;
	}

	@Override
	public Page<User> selectPage(Page<User> page, Wrapper<User> wrapper) {
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectUserList(page, wrapper));
		return page;
	}

	@Override
	public User register(User user) {
		user.setUsername(getRegisterUserName());
		insert(user);
		return user;
	}

	/**
	 * 获得用户名，循环获取，有可能获得是重复的情况
	 * @return
	 */
	public String getRegisterUserName(){
		String userName=StringUtils.randomNumber(8);
		User user=findByUsername(userName);
		if (user==null){
			return userName;
		}else{
			return getRegisterUserName();
		}
	}
}
