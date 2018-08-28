package com.gwg.shiro.web.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwg.shiro.web.dao.UserDao;
import com.gwg.shiro.web.dto.UserDto;
import com.gwg.shiro.web.exception.BusinessException;
import com.gwg.shiro.web.mapper.UserMapper;
import com.gwg.shiro.web.model.User;
import com.gwg.shiro.web.model.UserExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao{

    @Autowired
    private UserMapper userMapper;

    public User queryUserByUserId(String userId) {
        if(StringUtils.isEmpty(userId)){
            return null;
        }
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> userList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return userList.get(0);
    }


    public PageInfo<User> queryUserInfoByLimit(UserDto dto) throws BusinessException {
        PageHelper.startPage(dto.getPageIndex(), dto.getPageSize());
        UserExample example = new UserExample();
        if(StringUtils.isEmpty(dto.getUserId())){
            example.createCriteria().andUserIdEqualTo(dto.getUserId());
        }
        if(StringUtils.isEmpty(dto.getUsername())){
            example.createCriteria().andUsernameLike(dto.getUsername());
        }
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> page = new PageInfo<User>(userList);
        return page;

    }

    public boolean addUser(UserDto dto) throws BusinessException {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return userMapper.insertSelective(user) > 0;
    }

    public User queryUserInfoById(UserDto dto) throws BusinessException {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(dto.getId());
        List<User> userList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return userList.get(0);
    }

    public boolean updateUserByUserId(UserDto dto) throws BusinessException{
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(dto.getUserId());
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return userMapper.updateByExampleSelective(user, example) > 0;
    }
}
