package com.gwg.shiro.web.service.impl;

import com.github.pagehelper.PageInfo;
import com.gwg.shiro.web.dao.RoleDao;
import com.gwg.shiro.web.dao.UserDao;
import com.gwg.shiro.web.dto.UserDto;
import com.gwg.shiro.web.exception.BusinessException;
import com.gwg.shiro.web.model.User;
import com.gwg.shiro.web.service.UserService;
import com.gwg.shiro.web.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;


	@Override
	public boolean addUser(UserDto dto) {
		/**
		 * 获取Spring代理生成的类的全路径名
		 * 如果采用的是CGLIB的方式实现AOP，由于是直接修改UserDaoImpL的字节码，所以路径名为com.gwg.shiro.web.service.impl.UserServiceImpl
		 * 如果采用的是SpringAop的方式实现AOP，由于SpringAOP是基于java接口的，所以路径类似于com.sun.proxy.$Proxy78
		 */
		logger.info("获取Spring代理生成的类的全路径名**********:{}", userDao.getClass().getName());
		return userDao.addUser(dto);
	}

	/**
	 * 根据id查询用户信息
	 */
	public User queryUserInfoById(UserDto dto) {
		return userDao.queryUserInfoById(dto);
	}
}
