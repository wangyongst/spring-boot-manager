package com.spring.boot.manager.service;


import com.spring.boot.manager.utils.result.Result;

public interface ApiService {

    Result banding(String code);

    Result purchList(Integer status);

    Result purchAccept(Integer id);

    Result purch(Integer id);

    Result purchPrice(Integer id, String price);

    Result purchSend(Integer id);

    Result purchDeliver(Integer id, Integer delivernum);

    Result deliverList(Integer status);

    Result deliver(Integer id);

    Result deliverAccept(Integer id, Integer delivernum);

    Result deliverAccept(Integer id);
}
