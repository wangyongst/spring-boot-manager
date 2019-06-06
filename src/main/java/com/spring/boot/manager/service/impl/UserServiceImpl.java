package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.mapper.UserMapper;
import com.spring.boot.manager.repository.UserRepository;
import com.spring.boot.manager.utils.db.IdUtils;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.restful.RestfulUtil;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = true)
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private RestfulUtil restfulUtil;


    private UserRepository userRepository;


    private UserMapper userMapper;


    //事务处理，查询操作不要写这个注解
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
    public Result regist(User user) {
        //先必须判断参数是否为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())) {
            return ResultUtil.parameterNotEnoughWithMessage("必须参数不能为空");
        }
        //日志使用方法
        logger.warn("this is a test");
        //数据库id生成方法-调用utils微服务，所有系统统一这种办法
        user.setId(IdUtils.createId().getData().toString());
        //时间格式化方法-调用utils微服务，所有系统统一这种办法
        user.setCreatetime(TimeUtils.format(System.currentTimeMillis()).getData().toString());
        //spring data jpa写法
        User savedUser = userRepository.save(user);
        //mybatis写法
        List<User> savedUsers = userMapper.findByUsername(user.getUsername());
        savedUsers.forEach(e -> {
            System.out.println(e.getUsername() + "--" + e.getPassword());
        });
        //请求外部接口方法-调用utils微服务，所有系统统一这种办法
        Result bookResult = restfulUtil.get("https://api.douban.com/v2/book/isbn/9787208157408");
        System.out.println(bookResult.getData().toString());
        //Json字符串转对象方法
        ObjectMapper mapper = new ObjectMapper();
        try {
            User test = mapper.readValue(bookResult.getData().toString(), User.class);
        } catch (IOException e) {
            return ResultUtil.ioExceptionWithMessage(e.toString());
        }
        //对象实例转Json方法
        try {
            String json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return ResultUtil.jsonProcessingExceptionWithMessage(e.toString());
        }
        //异常处理方法-调用utils微服务，所有系统统一这种办法
        if (savedUser == null || savedUser.getId() == null) {
            Result result = ResultUtil.createErrorWithMessage("创建用户数据失败！");
            result.setData(user);
            return result;
        } else {
            //组装返回结果方法
            Result result = ResultUtil.okWithMessage("用户注册成功！");
            result.setData(savedUser);
            return result;
        }
    }
}
