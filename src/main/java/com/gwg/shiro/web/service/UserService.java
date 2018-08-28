package com.gwg.shiro.web.service;

import com.gwg.shiro.web.dto.UserDto;
import com.gwg.shiro.web.exception.BusinessException;
import com.gwg.shiro.web.model.User;

/**
 * Created by
 */
public interface UserService{


    /**
     * 新增用户
     */
    public boolean addUser(UserDto dto);

        /**
         * 根据userid查询用户信息
         */
    public User queryUserInfoById(UserDto dto) throws BusinessException;




}
