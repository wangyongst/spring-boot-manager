package com.spring.boot.manager.utils;

import com.spring.boot.manager.entity.Role;
import com.spring.boot.manager.entity.Role2Permission;

import java.util.ArrayList;
import java.util.List;

public class ThymeleafUtils {

    public boolean contains(int id, Role role) {
        List<Integer> ids = new ArrayList<>();
        for (Role2Permission r2p : role.getRole2Permissions()) {
            ids.add(r2p.getPermission().getId());
        }
        return (ids.contains(id));
    }

    public String contact(String str1, String str2) {
        return str1 + str2;
    }

}
