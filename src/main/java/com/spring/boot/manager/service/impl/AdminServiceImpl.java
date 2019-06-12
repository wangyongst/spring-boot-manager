package com.spring.boot.manager.service.impl;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.RoleRepository;
import com.spring.boot.manager.repository.UserRepository;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service("AdminOneService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Result me(HttpSession httpSession) {
        return ResultUtil.okWithData(httpSession.getAttribute("user"));
    }

    @Override
    public Result userList(AdminParameter adminParameter, HttpSession httpSession) {
        Pageable pageable = new PageRequest(adminParameter.getPage(), 10);
        return ResultUtil.okWithData(userRepository.findAll(pageable));
    }

    @Override
    public Result roleList(AdminParameter adminParameter, HttpSession httpSession) {
        Pageable pageable = new PageRequest(adminParameter.getPage(),10);
        return ResultUtil.okWithData(roleRepository.findAll(pageable));
    }

    @Override
    public Result user(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(userRepository.findById(adminParameter.getUserid()).get());
    }

    @Override
    public Result userSU(AdminParameter adminParameter, HttpSession httpSession) {
        User user = null;
        if (adminParameter.getUserid() == 0) {
            user = new User();
            user.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            user.setIschange(0);
            User me = (User) httpSession.getAttribute("user");
            user.setCreateusername(me.getUsername());
        } else {
            user = userRepository.findById(adminParameter.getUserid()).get();
        }
        user.setUsername(adminParameter.getUsername());
        user.setPassword(adminParameter.getPassword());
        user.setMobile(adminParameter.getMobile());
        user.setRole(roleRepository.findById(adminParameter.getRoleid()).get());
        userRepository.save(user);
        return ResultUtil.ok();
    }

    @Override
    public Result userDelete(AdminParameter adminParameter, HttpSession httpSession) {
        userRepository.delete(userRepository.findById(adminParameter.getUserid()).get());
        return ResultUtil.ok();
    }

    @Override
    public Result role(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(userRepository.findById(adminParameter.getRoleid()).get());
    }

    @Override
    public Result privilegeAll(AdminParameter adminParameter, HttpSession httpSession) {
        Sort sort = new Sort("id");
        return ResultUtil.okWithData(userRepository.findAll(sort));
    }

    @Override
    public Result login(AdminParameter adminParameter, HttpSession httpSession) {
        if (StringUtils.isBlank(adminParameter.getMobile())) return ResultUtil.errorWithMessage("登录账号不能为空！");
        if (StringUtils.isBlank(adminParameter.getPassword())) return ResultUtil.errorWithMessage("登录密码不能为空！");
        String regex = "^[a-z0-9A-Z]+$";
        if (!adminParameter.getPassword().matches(regex)) return ResultUtil.errorWithMessage("密码只支持数字和英文！");
        List<User> userList = userRepository.findByMobileAndPassword(adminParameter.getMobile(), adminParameter.getPassword());
        if (userList.size() == 1) {
            httpSession.setAttribute("user", userList.get(0));
            return ResultUtil.okWithData(userList.get(0));
        } else {
            return ResultUtil.errorWithMessage("您的用户名/密码错误，请重新输入！");
        }
    }


    @Override
    public Result changePassword(AdminParameter adminParameter, HttpSession httpSession) {
        if (StringUtils.isBlank(adminParameter.getPassword())) return ResultUtil.errorWithMessage("原录密码不能为空！");
        if (StringUtils.isBlank(adminParameter.getNewpassword())) return ResultUtil.errorWithMessage("新密码不能为空不能为空！");
        if (!adminParameter.getNewpassword().equals(adminParameter.getNewpassword2()))
            return ResultUtil.errorWithMessage("两次密码输入不一致，请重新输入！");
        if (adminParameter.getPassword().equals(adminParameter.getNewpassword()))
            return ResultUtil.errorWithMessage("新密码与原密码相同，请重新输入！");
        if (adminParameter.getNewpassword().length() < 3 || adminParameter.getNewpassword().length() > 20)
            return ResultUtil.errorWithMessage("密码长度不正确，请重新输入（最短3个字符，最长20个字符）（！");
        String regex = "^[a-z0-9A-Z]+$";
        if (!adminParameter.getNewpassword().matches(regex)) return ResultUtil.errorWithMessage("密码只包含数字和英文,其他字符不能输入！");
        User user = (User) httpSession.getAttribute("user");
        if (user.getPassword().equals(adminParameter.getPassword())) {
            user.setPassword(adminParameter.getNewpassword());
            user.setIschange(1);
            userRepository.save(user);
            return ResultUtil.ok();
        } else {
            return ResultUtil.errorWithMessage("原密码错误！");
        }
    }

    @Override
    public Result logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return ResultUtil.ok();
    }
}
