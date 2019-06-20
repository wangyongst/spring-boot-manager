package com.spring.boot.manager.service;

import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface AdminService {
    Result login(AdminParameter adminParameter, HttpSession httpSession);

    Result me(HttpSession httpSession);

    Result userList(AdminParameter adminParameter, HttpSession httpSession);

    Result roleList(AdminParameter adminParameter, HttpSession httpSession);

    Result user(AdminParameter adminParameter, HttpSession httpSession);

    Result userSud(AdminParameter adminParameter, HttpSession httpSession);

    Result role(AdminParameter adminParameter, HttpSession httpSession);

    Result roleSud(AdminParameter adminParameter, HttpSession httpSession);

    Result privilegeAll(AdminParameter adminParameter, HttpSession httpSession);

    Result changePassword(AdminParameter adminParameter, HttpSession httpSession);

    Result logout(HttpSession httpSession);

    Result upload(MultipartFile file,HttpSession httpSession);

}
