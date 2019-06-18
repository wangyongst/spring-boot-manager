package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.model.vo.UserV;
import com.spring.boot.manager.repository.UserRepository;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("ApiService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class ApiServiceImpl implements ApiService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(ApiServiceImpl.class);

    @Override
    public Result login(String mobile, String password) {
        if (StringUtils.isBlank(mobile)) return ResultUtil.errorWithMessage("登录账号不能为空！");
        if (StringUtils.isBlank(password)) return ResultUtil.errorWithMessage("登录密码不能为空！");
        String regex = "^[a-z0-9A-Z]+$";
        if (!password.matches(regex)) return ResultUtil.errorWithMessage("密码只支持数字和英文！");
        List<User> userList = userRepository.findByMobileAndPassword(mobile, password);
        if (userList.size() == 1) {
            User user = userList.get(0);
            UserV userV = new UserV();
            userV.setUserid(user.getId());
            return ResultUtil.okWithData(userV);
        } else {
            return ResultUtil.errorWithMessage("您的账号/密码错误，请重新输入！");
        }
    }
}
