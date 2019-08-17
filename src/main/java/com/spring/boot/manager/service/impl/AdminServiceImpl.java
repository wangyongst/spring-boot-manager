package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

;


@Service
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    @Value("${custom.file.path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private Role2PermissionRepository role2PermissionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SettingRepository settingRepository;


    @Override
    public Result me() {
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.okWithData(userRepository.findById(me.getId()).get());
    }

    @Override
    public Result findByUsername(String username) {
        List<User> userList = userRepository.findByMobile(username);
        if (userList.size() == 1) return ResultUtil.okWithData(userList.get(0));
        return null;
    }

    @Override
    public Result userList(AdminParameter adminParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        User user = new User();
        if (StringUtils.isNotBlank(adminParameter.getMobile())) user.setMobile(adminParameter.getMobile());
        if (StringUtils.isNotBlank(adminParameter.getName())) user.setName(adminParameter.getName());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("mobile", match -> match.contains())
                .withMatcher("name", match -> match.contains());
        Example<User> example = Example.of(user, matcher);
        return ResultUtil.okWithData(userRepository.findAll(example, sort));
    }

    @Override
    public Result roleList(AdminParameter adminParameter) {
        List<Role> roles = roleRepository.findAll();
        Role role = roleRepository.findById(13).get();
        roles.remove(role);
        return ResultUtil.okWithData(roles);
    }

    @Override
    public Result user(AdminParameter adminParameter) {
        return ResultUtil.okWithData(userRepository.findById(adminParameter.getUserid()).get());
    }

    @Override
    public Result userSud(AdminParameter adminParameter) {
        User user = null;
        if (adminParameter.getUserid() == 0) {
            if (StringUtils.isBlank(adminParameter.getMobile())) return ResultUtil.errorWithMessage("电话不能为空！");
            if (userRepository.findByMobile(adminParameter.getMobile()).size() > 0)
                return ResultUtil.errorWithMessage("电话已经存在！");
            user = new User();
            user.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            user.setIschange(0);
            User me = (User) SecurityUtils.getSubject().getPrincipal();
            user.setCreateusername(me.getName());
        } else {
            user = userRepository.findById(adminParameter.getUserid()).get();
            if (adminParameter.getDelete() != 0) {
                if (user.getId() == ((User) SecurityUtils.getSubject().getPrincipal()).getId())
                    SecurityUtils.getSubject().logout();
                deleteUser(user);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getMobile())) return ResultUtil.errorWithMessage("电话不能为空！");
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("登录姓名不能为空！");
        if (adminParameter.getName().length() > 10) return ResultUtil.errorWithMessage("登录姓名不能超过10个字！");
        String regex = "^[0-9]+$";
        if (!adminParameter.getMobile().matches(regex)) return ResultUtil.errorWithMessage("电话只能是数字！");
        if (adminParameter.getMobile().length() != 11) return ResultUtil.errorWithMessage("电话只能是11位数字！");
        if (StringUtils.isBlank(adminParameter.getPassword())) return ResultUtil.errorWithMessage("密码不能为空！");
        regex = "^[a-z0-9A-Z]+$";
        if (!adminParameter.getPassword().matches(regex)) return ResultUtil.errorWithMessage("密码只支持数字和英文！");
        if (adminParameter.getRoleid() == 0) return ResultUtil.errorWithMessage("配置角色未选择！");
        if (adminParameter.getRoleid() == -1) {
            if (adminParameter.getSupplierid() == 0) return ResultUtil.errorWithMessage("供应商未选择！");
            user.setSupplier(supplierRepository.findById(adminParameter.getSupplierid()).get());
            user.setRole(null);
            user.setDeliver(null);
        } else if (adminParameter.getRoleid() == -2) {
            user.setDeliver(1);
            user.setSupplier(null);
            user.setRole(null);
        } else {
            user.setSupplier(null);
            user.setDeliver(null);
            user.setRole(roleRepository.findById(adminParameter.getRoleid()).get());
        }
        user.setName(adminParameter.getName());
        user.setPassword(new Md5Hash(adminParameter.getPassword()).toHex());
        user.setMobile(adminParameter.getMobile());
        userRepository.save(user);
        return ResultUtil.ok();
    }

    @Override
    public Result role(AdminParameter adminParameter) {
        return ResultUtil.okWithData(roleRepository.findById(adminParameter.getRoleid()).get());
    }

    @Override
    public Result roleSud(AdminParameter adminParameter) {
        Role role = null;
        if (adminParameter.getRoleid() == 0) {
            role = new Role();
        } else {
            role = roleRepository.findById(adminParameter.getRoleid()).get();
            if (adminParameter.getDelete() != 0) {
                deleteRole(role);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("角色名称不能为空！");
        if (adminParameter.getName().length() > 10) return ResultUtil.errorWithMessage("角色名称最多10个字！");
        role.setName(adminParameter.getName());
        if (adminParameter.getProjectid() != 0)
            role.setProjectid(projectRepository.findById(adminParameter.getProjectid()).get().getId());
        Role saveedRole = roleRepository.save(role);
        role2PermissionRepository.deleteAllByRole(role);
        if (adminParameter.getPermission() != null && adminParameter.getPermission().size() > 0) {
            adminParameter.getPermission().forEach(e -> {
                Role2Permission role2Permission = new Role2Permission();
                role2Permission.setRole(saveedRole);
                role2Permission.setPermission(permissionRepository.findById(e).get());
                role2PermissionRepository.save(role2Permission);
            });
        }
        return ResultUtil.ok();
    }

    @Override
    public Result permissionList(AdminParameter adminParameter) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return ResultUtil.okWithData(permissionRepository.findAll(sort));
    }

    @Override
    public Result changePassword(AdminParameter adminParameter) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
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
        if (userRepository.findById(user.getId()).get().getPassword().equals(new Md5Hash(adminParameter.getPassword()).toHex())) {
            user.setPassword(new Md5Hash(adminParameter.getNewpassword()).toHex());
            user.setIschange(1);
            userRepository.save(user);
            return ResultUtil.ok();
        } else {
            return ResultUtil.errorWithMessage("原密码错误！");
        }
    }

    @Override
    public Result setting(AdminParameter adminParameter) {
        return ResultUtil.okWithData(settingRepository.findByType(adminParameter.getType()).get(0));
    }

    @Override
    public Result settingSud(AdminParameter adminParameter) {
        if (adminParameter.getType() == 1) {
            if (StringUtils.isBlank(adminParameter.getValue())) return ResultUtil.errorWithMessage("价格系数不能为空！");
            if (!adminParameter.getValue().matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
                return ResultUtil.errorWithMessage("价格系数只能是两位以内小数或整数！");
            Setting setting = settingRepository.findByType(adminParameter.getType()).get(0);
            setting.setValue(BigDecimal.valueOf(Double.parseDouble(adminParameter.getValue())));
            settingRepository.save(setting);
            return ResultUtil.ok();
        } else if (adminParameter.getType() == 2) {
            if (StringUtils.isBlank(adminParameter.getAccepttime())) return ResultUtil.errorWithMessage("接单时间不能为空！");
            if (StringUtils.isBlank(adminParameter.getPricetime())) return ResultUtil.errorWithMessage("询价时间不能为空！");
            String regex = "^[0-9]+$";
            if (!adminParameter.getAccepttime().matches(regex)) return ResultUtil.errorWithMessage("接单时间只能是数字！");
            if (!adminParameter.getPricetime().matches(regex)) return ResultUtil.errorWithMessage("询价时间只能是数字！");
            Setting setting2 = settingRepository.findByType(2).get(0);
            setting2.setValue(new BigDecimal(adminParameter.getPricetime()));
            settingRepository.save(setting2);
            Setting setting3 = settingRepository.findByType(3).get(0);
            setting3.setValue(new BigDecimal(adminParameter.getAccepttime()));
            settingRepository.save(setting2);
        }
        return ResultUtil.ok();
    }

    @Override
    public Result upload(MultipartFile file, AdminParameter adminParameter) {
        String fileName = adminParameter.getResourceid() + "-" + file.getOriginalFilename();
        String path = uploadPath + fileName;
        File dest = new File(path);
        try {
            file.transferTo(dest);
            Resource resource = resourceRepository.findById(adminParameter.getResourceid()).get();
            resource.setFile(fileName);
            resourceRepository.save(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.errorWithMessage("文件上传失败！");
        } finally {
            return ResultUtil.okWithData(fileName);
        }
    }

    @Override
    public void deleteRole(Role role) {
        userRepository.findByRole(role).forEach(e -> {
            e.setRole(null);
            userRepository.save(e);
        });
        role2PermissionRepository.deleteAllByRole(role);
        roleRepository.delete(role);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
