package com.gwg.shiro.web.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gwg.shiro.web.dao.RoleDao;
import com.gwg.shiro.web.dto.RoleDto;
import com.gwg.shiro.web.exception.BusinessException;
import com.gwg.shiro.web.mapper.RoleMapper;
import com.gwg.shiro.web.model.Role;
import com.gwg.shiro.web.model.RoleExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RoleDaoImpl implements RoleDao{

    @Autowired
    private RoleMapper roleMapper;


    public boolean addRole(RoleDto dto) throws BusinessException {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        role.setCreateTime(new Date());
        return roleMapper.insertSelective(role) > 0;

    }

    public boolean updateRoleById(RoleDto dto) throws BusinessException {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        role.setCreateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(role) > 0 ;
    }

    public boolean delRoleById(RoleDto dto) throws BusinessException {
        Role role = new Role();
        role.setId(dto.getId());
        return roleMapper.updateByPrimaryKey(role) > 0;
    }

    public Role queryRoleById(RoleDto dto) throws BusinessException {
        return roleMapper.selectByPrimaryKey(dto.getId());
    }

    public PageInfo<Role> queryRoleByLimit(RoleDto dto) throws BusinessException{
        PageHelper.startPage(dto.getPageIndex(), dto.getPageSize());
        RoleExample example = new RoleExample();
        if(StringUtils.isEmpty(dto.getRoleCode())){
            example.createCriteria().andRoleCodeEqualTo(dto.getRoleCode());
        }
        if(StringUtils.isEmpty(dto.getRoleName())){
            example.createCriteria().andRoleNameLike(dto.getRoleName());
        }
        List<Role> roleList = roleMapper.selectByExample(example);
        return new PageInfo<Role>(roleList);
    }

}
