package com.spring.boot.manager.service;

import com.spring.boot.manager.entity.Role;
import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Result me();

    Result findByUsername(String username);

    Result userList(AdminParameter adminParameter);

    Result roleList(AdminParameter adminParameter);

    Result user(AdminParameter adminParameter);

    Result userSud(AdminParameter adminParameter);

    Result role(AdminParameter adminParameter);

    Result roleSud(AdminParameter adminParameter);

    Result permissionList(AdminParameter adminParameter);

    Result changePassword(AdminParameter adminParameter);

    Result setting(AdminParameter adminParameter);

    Result settingSud(AdminParameter adminParameter);

    Result upload(MultipartFile file,AdminParameter adminParameter);

    void deleteRole(Role role);

}
