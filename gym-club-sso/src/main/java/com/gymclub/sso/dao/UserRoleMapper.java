package com.gymclub.sso.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author Xiaoming.
 * Created on 2019/05/27 20:26.
 */
public interface UserRoleMapper {
    int insert(@Param("userId") int userId, @Param("roleId") int roleId);
}
