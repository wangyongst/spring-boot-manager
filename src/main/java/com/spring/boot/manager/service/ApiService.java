package com.spring.boot.manager.service;


import com.spring.boot.manager.utils.result.Result;

public interface ApiService {

    Result purchList(Integer status);

    Result purchAccept(Integer id);

    Result purch(Integer id);

    Result purchPrice(Integer id, String price);

    Result purchSend(Integer id);

    Result purchDeliver(Integer id, Integer delivernum);
}
