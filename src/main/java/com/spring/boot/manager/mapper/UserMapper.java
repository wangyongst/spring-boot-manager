package com.spring.boot.manager.mapper;

import com.spring.boot.manager.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    List<User> findByUsername(@Param("username") String username);
}
