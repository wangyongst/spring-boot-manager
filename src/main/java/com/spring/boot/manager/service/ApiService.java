package com.spring.boot.manager.service;


import com.spring.boot.manager.utils.result.Result;

public interface ApiService {

    Result login(String mobile, String password);

}
