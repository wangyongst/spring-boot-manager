package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.*;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminOneService;
import com.spring.boot.manager.utils.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service("AdminOneService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class AdminOneServiceImpl implements AdminOneService {

    private static final Logger logger = LogManager.getLogger(AdminOneServiceImpl.class);

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private TimenewRepository timenewRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private SearchingRepository searchingRepository;

    @Autowired
    private ReferipsRepository referipsRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private AdminLogRepository adminLogRepository;

    @Autowired
    private User2Repository user2Repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private AdminMenuRepository adminMenuRepository;

    @Autowired
    private AdminPrivilegeRepository adminPrivilegeRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public Result login(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (StringUtils.isNotBlank(oneParameter.getUsername()) || StringUtils.isNotBlank(oneParameter.getPassword())) {
            List<AdminUser> userList = adminUserRepository.findByUsernameAndPassword(oneParameter.getUsername(), oneParameter.getPassword());
            if (userList.size() == 1) {
                httpSession.setAttribute("user", userList.get(0));
                createLog("登录", httpSession);
                result.setStatus(1);
                result.setData(userList.get(0));
                return result;
            } else if (userList.size() != 1) {
                result.setMessage("用户不存在或密码错误！");
                return result;
            }
        } else {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setMessage("登录失败！");
        return result;
    }

    @Override
    public Result userMe(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(httpSession.getAttribute("user"));
        return result;
    }

    @Override
    public Result userAdminList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminUserRepository.findAll());
        return result;
    }

    @Override
    public Result userAdmin(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminUserRepository.findById(fourParameter.getUserid()).get());
        return result;
    }

    @Override
    public Result postUserAdmin(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        if (fourParameter.getType() == 1) {
            if (fourParameter.getUserid() == null || fourParameter.getUserid() == 0) {
                AdminUser adminUser = new AdminUser();
                adminUser.setUsername(fourParameter.getUsername());
                adminUser.setPassword(fourParameter.getPassword());
                adminUser.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                adminUser.setAdminRole(adminRoleRepository.findById(fourParameter.getRoleid()).get());
                adminUserRepository.save(adminUser);
                createLog("新建后台用户:" + adminUser.getUsername(), httpSession);
                result.setStatus(1);
            } else {
                AdminUser adminUser = adminUserRepository.findById(fourParameter.getUserid()).get();
                adminUser.setUsername(fourParameter.getUsername());
                adminUser.setPassword(fourParameter.getPassword());
                adminUser.setAdminRole(adminRoleRepository.findById(fourParameter.getRoleid()).get());
                adminUserRepository.save(adminUser);
                createLog("修改后台用户:" + adminUser.getUsername(), httpSession);
                result.setStatus(1);
            }
        } else if (fourParameter.getType() == 2) {
            AdminUser adminUser = adminUserRepository.findById(fourParameter.getUserid()).get();
            adminLogRepository.deleteAllByAdminUser(adminUser);
            advertRepository.deleteAllByAdminuser(adminUser);
            adminUserRepository.delete(adminUser);
            createLog("删除后台用户:" + adminUser.getUsername(), httpSession);
            result.setStatus(1);
        }
        return result;
    }


    @Override
    public Result postUserRole(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        if (fourParameter.getType() == 1) {
            if (fourParameter.getRoleid() == null || fourParameter.getRoleid() == 0) {
                AdminRole adminRole = new AdminRole();
                adminRole.setName(fourParameter.getName());
                adminRole.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                adminRoleRepository.save(adminRole);
                createLog("新建后台角色:" + adminRole.getName(), httpSession);
                result.setStatus(1);
            } else {
                AdminRole adminRole = adminRoleRepository.findById(fourParameter.getRoleid()).get();
                adminRole.setName(fourParameter.getName());
                adminRoleRepository.save(adminRole);
                createLog("修改后台角色:" + adminRole.getName(), httpSession);
                result.setStatus(1);
            }
        } else if (fourParameter.getType() == 2) {
            AdminRole adminRole = adminRoleRepository.findById(fourParameter.getRoleid()).get();
            adminRole.getAdminPrivileges().forEach(e -> {
                adminPrivilegeRepository.delete(e);
            });
            adminUserRepository.findAllByAdminRole(adminRole).forEach(e -> {
                e.setAdminRole(null);
                adminUserRepository.save(e);
            });
            adminRoleRepository.delete(adminRole);
            createLog("删除后台角色:" + adminRole.getName(), httpSession);
            result.setStatus(1);
        }
        return result;
    }


    @Override
    public Result postUserPrivilege(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        String[] ids = fourParameter.getMenuids().split(",");
        AdminRole adminRole = adminRoleRepository.findById(fourParameter.getRoleid()).get();
        adminRole.getAdminPrivileges().forEach(e -> {
            adminPrivilegeRepository.delete(e);
        });
        for (String id : ids) {
            AdminPrivilege adminPrivilege = new AdminPrivilege();
            adminPrivilege.setAdminRole(adminRole);
            adminPrivilege.setAdminMenu(adminMenuRepository.findById(Integer.parseInt(id)).get());
            adminPrivilege.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            adminPrivilegeRepository.save(adminPrivilege);
        }
        createLog("修改后台权限，角色:" + adminRole.getName(), httpSession);
        return result;
    }

    @Override
    public Result userRoleList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminRoleRepository.findAll());
        return result;
    }

    @Override
    public Result userPrivilege(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        AdminRole adminRole = adminRoleRepository.findById(fourParameter.getRoleid()).get();
        result.setData(adminPrivilegeRepository.findAllByAdminRole(adminRole));
        return result;
    }

    @Override
    public Result userRole(FourParameter fourParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminRoleRepository.findById(fourParameter.getRoleid()));
        return result;
    }

    @Override
    public Result userMenuList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminMenuRepository.findAll());
        return result;
    }

    @Override
    public Result logout(HttpSession httpSession) {
        Result result = new Result();
        createLog("登出", httpSession);
        httpSession.removeAttribute("user");
        result.setStatus(1);
        return result;
    }


    @Override
    public Result userRole15(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminUserRepository.findAllByAdminRole(adminRoleRepository.findById(15).get()));
        return result;
    }

    @Override
    public Result userList(AdminOneParameter adminOneParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (adminOneParameter.getOffset() == null) adminOneParameter.setOffset(0);
        if (adminOneParameter.getLimit() == null) adminOneParameter.setLimit(10);
        Pageable pageable = new PageRequest(adminOneParameter.getOffset()/10, adminOneParameter.getLimit(), sort);
        Page<User2> user2 = user2Repository.findAllByIdNot(1, pageable);
        TableVo tableVo = new TableVo();
        tableVo.setRows(user2.getContent());
        tableVo.setTotal(user2.getTotalElements());
        result.setData(tableVo);
        return result;
    }

    @Override
    public Result countToken(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(tokenRepository.countAllByExpiretimeGreaterThanAndOuttimeIsNull(new Date().getTime()));
        return result;
    }

    @Override
    public Result userTokenList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(tokenRepository.findAllByOrderByCreatetimeDesc());
        return result;
    }

    @Override
    public Result searchingList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(searchingRepository.findAllTimes());
        return result;
    }

    @Override
    public Result userGroupList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(userRepository.findAllTimes());
        return result;
    }

    @Override
    public Result reportListUser(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(reportRepository.findAllByTouserIsNotNullOrderByCreatetimeDesc());
        return result;
    }

    @Override
    public Result reportListHelp(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(reportRepository.findAllByHelpIsNotNullOrderByCreatetimeDesc());
        return result;
    }

    @Override
    public Result user(OneParameter oneParameter, HttpSession httpSession) throws ParseException {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) return result;
        result.setStatus(1);
        if (oneParameter.getType() != null && oneParameter.getType() == 2) {
            createLog("删除ID为" + oneParameter.getUserid() + "的用户", httpSession);
            User user = userRepository.findById(oneParameter.getUserid()).get();
            buyRepository.removeAllByUser(user);
            followRepository.removeAllByUserOrTouser(user, user);
            List<Help> helps = helpRepository.findByUser(user);
            helps.forEach(e -> {
                studyRepository.deleteAllByHelp(e);
                reportRepository.deleteAllByHelp(e);
                referipsRepository.deleteAllByHelp(e);
                noticeRepository.deleteAllByHelp(e);
                messageRepository.removeAllByHelp(e);
            });
            helpRepository.removeAllByUser(user);
            studyRepository.removeAllByUser(user);
            messageRepository.removeAllByUserOrTouser(user, user);
            noticeRepository.removeAllByUser(user);
            noticeRepository.removeAllByFromuser(user);
            searchingRepository.removeAllByUser(user);
            tokenRepository.removeAllByUser(user);
            reportRepository.deleteAllByUser(user);
            timenewRepository.deleteAllByUser(user);
            referipsRepository.deleteAllByUser(user);
            List<User> users = userRepository.findByRefer(user.getId());
            users.forEach(e -> {
                e.setRefer(null);
                userRepository.save(e);
            });
            userRepository.delete(user);
        } else if (oneParameter.getType() != null && oneParameter.getType() == 1) {
            User user = userRepository.findById(oneParameter.getUserid()).get();
            if (StringUtils.isNotBlank(oneParameter.getEmail())) {
                createLog("修改ID为" + user.getId() + "的用户的邮箱", httpSession);
                user.setEmail(oneParameter.getEmail());
            }
            result.setData(userRepository.save(user));
        } else if (oneParameter.getType() != null && oneParameter.getType() == 3) {
            User user = userRepository.findById(oneParameter.getUserid()).get();
            AdminUser adminUser = new AdminUser();
            adminUser.setUsername(user.getUsername());
            adminUser.setPassword(user.getPassword());
            adminUser.setAdminRole(adminRoleRepository.findById(15).get());
            adminUserRepository.save(adminUser);
            user.setRefertime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd").parse(oneParameter.getOuttime())));
            userRepository.save(user);
        }
        return result;
    }

    @Override
    public Result getUser(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) return result;
        result.setStatus(1);
        result.setData(userRepository.findById(oneParameter.getUserid()));
        return result;
    }

    @Override
    public Result userLock(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) return result;
        result.setStatus(1);
        User user = userRepository.findById(oneParameter.getUserid()).get();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        user.setLocktime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(calendar.getTime()));
        userRepository.save(user);
        List<Report> reports = reportRepository.findAllByTouserIsNotNullOrderByCreatetimeDesc();
        reports.forEach(e -> {
            createSysNotice(user, null, "您在" + e.getCreatetime() + ",对\"" + e.getTouser().getNickname() + "\"的举报已确认存在违规行为，并已经对举报对象进行了处理 。", 5, 6, null);
        });
        reportRepository.deleteAllByTouser(user);
        return result;
    }

    @Override
    public Result studyCount(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(helpRepository.sumStudied());
        return result;
    }


    @Override
    public Result countUser(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(userRepository.count());
        return result;
    }

    @Override
    public Result messageList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(messageRepository.findAllByUserNotOrderByCreatetimeDesc(userRepository.findById(1).get()));
        return result;
    }

    @Override
    public Result countMessageUser(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (oneParameter.getType() == null && oneParameter.getType() == 0) return result;
        if (oneParameter.getUserid() == null && oneParameter.getUserid() == 0) return result;
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (oneParameter.getType() == 1) {
            result.setStatus(1);
            result.setData(messageRepository.countAllByUser(user));
        } else if (oneParameter.getType() == 2) {
            result.setStatus(1);
            result.setData(messageRepository.countAllByTouser(user));
        } else if (oneParameter.getType() == 3 && oneParameter.getTouserid() != null && oneParameter.getTouserid() != 0) {
            User touser = userRepository.findById(oneParameter.getTouserid()).get();
            result.setStatus(1);
            result.setData(messageRepository.countAllByUserAndTouser(user, touser));
        }
        return result;
    }

    @Override
    public Result help(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        if (twoParameter.getHelpid() == null || twoParameter.getHelpid() == 0) return result;
        result.setStatus(1);
        Help help = helpRepository.findById(twoParameter.getHelpid()).get();
        result.setData(help);
        return result;
    }

    @Override
    public Result advert(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        if (twoParameter.getAdvertid() == null || twoParameter.getAdvertid() == 0) return result;
        result.setStatus(1);
        result.setData(advertRepository.findById(twoParameter.getAdvertid()));
        return result;
    }

    @Override
    public Result helpList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(helpRepository.findAll());
        return result;
    }

    @Override
    public Result advertList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        List<Advert> adverts = advertRepository.findAll();
        adverts.forEach(e -> {
            e.setRate((float) e.getClicked() / e.getExposure());
        });
        result.setData(adverts);
        return result;
    }

    @Override
    public Result studyHelpList(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(studyRepository.queryAllByHelp(helpRepository.findById(oneParameter.getHelpid()).get()));
        return result;
    }

    @Override
    public Result userSendMessage(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        List<User> userList = studyRepository.queryAllByHelp(helpRepository.findById(oneParameter.getHelpid()).get());
        userList.forEach(e -> {
            createSysNotice(e, helpRepository.findById(oneParameter.getHelpid()).get(), oneParameter.getText(), 5, 7, oneParameter.getAdminuserid());
        });
        return result;
    }

    @Override
    public Result postHelp(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        if (twoParameter.getHelpid() == null || twoParameter.getHelpid() == 0) return result;
        Help help = helpRepository.findById(twoParameter.getHelpid()).get();
        if (help == null) return result;
        result.setStatus(1);
        if (twoParameter.getType() != null && twoParameter.getType() == 2) {
            createLog("删除ID为" + twoParameter.getHelpid() + "的求助", httpSession);
            studyRepository.deleteAllByHelp(help);
            reportRepository.deleteAllByHelp(help);
            referipsRepository.deleteAllByHelp(help);
            noticeRepository.deleteAllByHelp(help);
            messageRepository.removeAllByHelp(help);
            helpRepository.delete(help);
        } else if (twoParameter.getType() != null && twoParameter.getType() == 1 && twoParameter.getDraft() != null && twoParameter.getDraft() != 0) {
            createLog("审核ID为" + twoParameter.getHelpid() + "的求助", httpSession);
            help.setRecommend(new Random().nextInt(700) + 300);
            help.setDraft(twoParameter.getDraft());
            helpRepository.save(help);
        }
        return result;
    }

    @Override
    public Result helpDraft(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(helpRepository.findByDraft(2));
        return result;
    }

    @Override
    public Result helpRefer(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        if (twoParameter.getHelpid() == null || twoParameter.getHelpid() == 0) return result;
        Help help = helpRepository.findById(twoParameter.getHelpid()).get();
        if (help == null) return result;
        help.setRefertime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        helpRepository.save(help);
        createLog("设置ID为" + help.getId() + "的求助为强制推荐", httpSession);
        return result;
    }

    @Override
    public Result advertRefer(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        if (twoParameter.getAdvertid() == null || twoParameter.getAdvertid() == 0) return result;
        Advert advert = advertRepository.findById(twoParameter.getAdvertid()).get();
        if (advert == null) return result;
        advert.setRefer(1);
        advertRepository.save(advert);
        createLog("设置ID为" + advert.getId() + "的广告为强制推荐", httpSession);
        return result;
    }

    @Override
    public Result advertOut(TwoParameter twoParameter, HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        if (twoParameter.getAdvertid() == null || twoParameter.getAdvertid() == 0) return result;
        Advert advert = advertRepository.findById(twoParameter.getAdvertid()).get();
        if (advert == null) return result;
        advert.setRefer(2);
        advertRepository.save(advert);
        createLog("下线ID为" + advert.getId() + "的广告", httpSession);
        return result;
    }

    @Override
    public Result setting(AdminOneParameter adminOneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (adminOneParameter.getOperation() == 1) {
            if (adminOneParameter.getSettingid() == null || adminOneParameter.getSettingid() == 0) {
                Setting setting = new Setting();
                setting.setName(adminOneParameter.getName());
                setting.setContent(adminOneParameter.getContent());
                setting.setType(adminOneParameter.getType());
                setting.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                createLog("增加网站设置", httpSession);
                result.setStatus(1);
                result.setData(settingRepository.save(setting));
            } else {
                Setting setting = settingRepository.findById(adminOneParameter.getSettingid()).get();
                result.setStatus(1);
                createLog("更改网站设置", httpSession);
                setting.setName(adminOneParameter.getName());
                setting.setContent(adminOneParameter.getContent());
                setting.setType(adminOneParameter.getType());
                result.setData(settingRepository.save(setting));
                result.setStatus(1);
            }
        } else if (adminOneParameter.getOperation() == 2) {
            settingRepository.deleteById(adminOneParameter.getSettingid());
            result.setStatus(1);
        }
        return result;
    }

    @Override
    public Result getSetting(AdminOneParameter adminOneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (adminOneParameter.getSettingid() == null || adminOneParameter.getSettingid() == 0) return result;
        result.setStatus(1);
        result.setData(settingRepository.findById(adminOneParameter.getSettingid()));
        return result;
    }

    @Override
    public Result settingList(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(settingRepository.findAll());
        return result;
    }

    @Override
    public Result postAdvert(AdminOneParameter adminOneParameter, HttpSession httpSession) {
        Result result = new Result();
        Advert advert = new Advert();
        if (adminOneParameter.getOperation() == 1) {
            advert.setAdminuser(adminUserRepository.findById(adminOneParameter.getAdminuserid()).get());
            advert.setTitle(adminOneParameter.getTitle());
            advert.setImage(adminOneParameter.getImage());
            advert.setUrl(adminOneParameter.getUrl());
            advert.setType(adminOneParameter.getType());
            advert.setExposure(0);
            advert.setClicked(0);
            advert.setBuy(0);
            advert.setForwarded(0);
            advert.setRefer(0);
            advert.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            try {
                advert.setOuttime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd").parse(adminOneParameter.getOuttime())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            createLog("上传广告", httpSession);
            result.setStatus(1);
            result.setData(advertRepository.save(advert));
        } else if (adminOneParameter.getOperation() == 2) {
            advert = advertRepository.findById(adminOneParameter.getAdvertid()).get();
            if (advert == null) return result;
            result.setStatus(1);
            createLog("更改广告", httpSession);
            advert.setTitle(adminOneParameter.getTitle());
            advert.setImage(adminOneParameter.getImage());
            advert.setUrl(adminOneParameter.getUrl());
            advert.setType(adminOneParameter.getType());
            advert.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            result.setData(advertRepository.save(advert));
        } else if (adminOneParameter.getOperation() == 3) {
            advert = advertRepository.findById(adminOneParameter.getAdvertid()).get();
            if (advert == null) return result;
            buyRepository.deleteAllByAdvert(advert);
            advertRepository.delete(advert);
            result.setStatus(1);
        }
        return result;
    }

    @Override
    public Result countFollow(OneParameter oneParameter, HttpSession httpSession) {
        Result result = new Result();
        if (oneParameter.getUserid() == null && oneParameter.getUserid() == 0) return result;
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null) return result;
        createLog("查询ID为" + user.getId() + "的用户的被关注总数", httpSession);
        result.setStatus(1);
        result.setData(followRepository.countAllByTouser(user));
        return result;
    }

    @Override
    public Result follow(OneParameter oneParameter, Pageable pageable, HttpSession httpSession) {
        Result result = new Result();
        if (oneParameter.getUserid() == null && oneParameter.getUserid() == 0) return result;
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null) return result;
        createLog("查询ID为" + user.getId() + "的用户的被详情", httpSession);
        result.setStatus(1);
        result.setData(followRepository.findByTouser(user, pageable));
        return result;
    }

    @Override
    public Result showlog(HttpSession httpSession) {
        Result result = new Result();
        result.setStatus(1);
        result.setData(adminLogRepository.findAll(new Sort(Sort.Direction.DESC, "createtime")));
        return result;
    }

    public void createLog(String message, HttpSession httpSession) {
        AdminLog adminLog = new AdminLog();
        AdminUser adminUser = (AdminUser) httpSession.getAttribute("user");
        adminLog.setAdminUser(adminUserRepository.findById(adminUser.getId()).get());
        adminLog.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        adminLog.setMessage(message);
        adminLogRepository.saveAndFlush(adminLog);
    }

    public void createSysNotice(User user, Help help, String message, Integer type, Integer mtype, Integer adminuserid) {
        Notice notice = new Notice();
        notice.setUser(user);
        notice.setFromuser(userRepository.findById(1).get());
        notice.setHelp(help);
        notice.setType(type);
        notice.setIsread(0);
        notice.setMessage(message);
        notice.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        noticeRepository.save(notice);
        Message me = new Message();
        me.setIsread(0);
        if (help != null) me.setHelp(help);
        me.setType(mtype);
        me.setMessage(message);
        me.setTouser(user);
        me.setUser(userRepository.findById(1).get());
        me.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        me.setAdminuser(adminuserid);
        messageRepository.save(me);
    }
}
