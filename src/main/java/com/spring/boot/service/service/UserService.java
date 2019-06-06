package com.spring.boot.service.service;


import com.spring.boot.service.entity.User;
import com.spring.boot.service.utils.Result;

public interface UserService {

    Result regist(User user);
}
