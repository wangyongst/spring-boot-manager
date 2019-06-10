package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminOneParameter;
import com.spring.boot.manager.model.MsgVo;
import com.spring.boot.manager.model.OneParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.OneService;
import com.spring.boot.manager.utils.http.HttpClientUtil;
import com.spring.boot.manager.utils.result.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service("OneService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class OneServiceImpl implements OneService {

    private static final Logger logger = LogManager.getLogger(OneServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private CaptchaRepository captchaRepository;

    @Autowired
    private TimenewRepository timenewRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private ReferipsRepository referipsRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SearchingRepository searchingRepository;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private FollowRepository followRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result regist(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isBlank(oneParameter.getUsername()) || StringUtils.isBlank(oneParameter.getMobile()) || StringUtils.isBlank(oneParameter.getPassword()) || StringUtils.isBlank(oneParameter.getNickname()) || StringUtils.isBlank(oneParameter.getText())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        if (captchaRepository.findByMobileAndCode(oneParameter.getMobile(), oneParameter.getText()).size() == 0) {
            result.setMessage("验证码不正确！");
            return result;
        }
        if (userRepository.findByUsername(oneParameter.getUsername()).size() > 0) {
            result.setMessage("手机号已经被占用!");
            return result;
        }
        User user = new User();
        if (oneParameter.getRefer() != null && oneParameter.getRefer() != 0) {
            User refer = userRepository.findById(oneParameter.getRefer()).get();
            if (refer == null) {
                result.setMessage("推荐人不存在!");
                return result;
            } else {
                user.setRefer(refer.getId());
                createSysNotice(refer, null, "恭喜你，成功邀请好友注册，获得平台永久发布功能。", 5, 2);
            }
        }
        user.setUsername(oneParameter.getUsername());
        user.setPassword(oneParameter.getPassword());
        user.setMobile(oneParameter.getMobile());
        user.setAvatar("http://pas99p7vd.bkt.clouddn.com/eM1jGVzQ");
        user.setNickname(oneParameter.getNickname());
        user.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        userRepository.save(user);
        createSysNotice(user, null, "邀请 1 个好友成功注册，你就可以获得平台免费永久发布想学，立即邀请", 5, 3);
        createSysNotice(user, null, "恭喜你，成功注册热点设计，你已获得 1 次免费使用发布功能，邀请一个好友注册成功，获得平台永久发布效果功能", 5, 4);
        result.setStatus(1);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result login(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isNotBlank(oneParameter.getUsername()) && StringUtils.isNotBlank(oneParameter.getPassword())) {
            List<User> users = userRepository.findByUsernameAndLocktimeGreaterThan(oneParameter.getUsername(), new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            if (users.size() > 0) {
                result.setStatus(2);
                result.setMessage("账号被冻结");
                return result;
            }
            List<User> userList = userRepository.findByUsernameAndPassword(oneParameter.getUsername(), oneParameter.getPassword());
            if (userList.size() == 1) {
                result.setStatus(1);
                Token token = new Token();
                token.setUser(userList.get(0));
                token.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                token.setExpiretime(new Date().getTime() + 60000 * 120);
                tokenRepository.save(token);
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result sendCaptcha(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isBlank(oneParameter.getMobile())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        } else {
            if (captchaRepository.findByMobileAndCreatetimeGreaterThan(oneParameter.getMobile(), new SimpleDateFormat("yyyy年MM月dd日").format(new Date())).size() >= 3) {
                result.setMessage("获取短信验证码每天不能超过三次!");
                return result;
            }
            String code = RandomStringUtils.randomNumeric(6);
            try {
                HttpClientUtil httpClientUtil = new HttpClientUtil();
                ObjectMapper objectMapper = new ObjectMapper();
                String tplid = "2430168";
                if (oneParameter.getType() != null && oneParameter.getType() == 1) tplid = "2442708";
                if (oneParameter.getType() != null && oneParameter.getType() == 2) tplid = "2442688";
                MsgVo msgVo = objectMapper.readValue(EntityUtils.toString(httpClientUtil.sendMessage(oneParameter.getMobile(), code, tplid).getEntity()), MsgVo.class);
                if (msgVo.getCode() == 0) {
                    Captcha captcha = new Captcha();
                    captcha.setMobile(oneParameter.getMobile());
                    captcha.setCode(code);
                    captcha.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                    captchaRepository.save(captcha);
                    result.setStatus(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result checkCaptcha(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isBlank(oneParameter.getMobile()) || StringUtils.isBlank(oneParameter.getText())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        } else {
            if (captchaRepository.findByMobileAndCode(oneParameter.getMobile(), oneParameter.getText()).size() > 0) {
                result.setStatus(1);
            } else {
                result.setMessage("验证码不正确！");
            }
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result advertLogin(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isNotBlank(oneParameter.getUsername()) || StringUtils.isNotBlank(oneParameter.getPassword())) {
            List<AdminUser> userList = adminUserRepository.findByUsernameAndPassword(oneParameter.getUsername(), oneParameter.getPassword());
            if (userList.size() == 1 && userList.get(0).getAdminRole().getId() == 15) {
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result advertLogout(OneParameter oneParameter) {
        Result result = new Result();
        result.setStatus(1);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result logout(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Token token = tokenRepository.findTop1ByUserOrderByCreatetimeDesc(user);
            token.setOuttime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            result.setStatus(1);
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result destroy(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || StringUtils.isBlank(oneParameter.getPassword()) || StringUtils.isBlank(oneParameter.getText())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            if (!user.getPassword().equals(oneParameter.getPassword())) {
                result.setMessage("密码不正确!");
            } else {
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
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result setBasic(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            user.setNickname(oneParameter.getNickname());
            user.setEmail(oneParameter.getEmail());
            user.setJobs(oneParameter.getJobs());
            user.setSex(oneParameter.getSex());
            userRepository.save(user);
            result.setStatus(1);
            result.setData(user);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result weixinCode(String code) {
        Result result = new Result();
        if (StringUtils.isBlank(code)) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        String response = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(response, User.class);
            if (StringUtils.isNotBlank(user.getOpenid())) {
                List<User> userList = userRepository.findByOpenid(user.getOpenid());
                if (userList.size() == 1) {
                    result.setStatus(1);
                    result.setData(userList.get(0));
                    Token token = new Token();
                    token.setUser(userList.get(0));
                    token.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                    token.setExpiretime(new Date().getTime() + 60000 * 120);
                    tokenRepository.save(token);
                    return result;
                } else if (userList.size() == 0) {
                    userRepository.save(user);
                    result.setStatus(1);
                    result.setData(user);
                    Token token = new Token();
                    token.setUser(user);
                    token.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                    token.setExpiretime(new Date().getTime() + 60000 * 120);
                    tokenRepository.save(token);
                    return result;
                } else {
                    result.setMessage("openid存在重复记录");
                    return result;
                }
            } else {
                result.setMessage("授权失败，无法获取openid");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result setAvatar(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || StringUtils.isBlank(oneParameter.getAvatar())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            user.setAvatar(oneParameter.getAvatar());
            userRepository.save(user);
            result.setStatus(1);
            result.setData(user);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result setPassword(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || StringUtils.isBlank(oneParameter.getPassword()) || StringUtils.isBlank(oneParameter.getPassword2())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else if (StringUtils.equals(user.getPassword(), oneParameter.getPassword())) {
            user.setPassword(oneParameter.getPassword2());
            userRepository.save(user);
            result.setStatus(1);
            result.setData(user);
        } else {
            result.setMessage("旧密码不正确！");
        }
        createSysNotice(user, null, "您的登录密码重置成功！\n为了保障您的账户安全，请保管好并定期更改登录及支付密码。", 5, 5);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result setEmail(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || StringUtils.isBlank(oneParameter.getMobile()) || StringUtils.isBlank(oneParameter.getPassword())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        if (userRepository.findByUsername(oneParameter.getMobile()).size() > 0) {
            result.setMessage("手机号已经已经被注册!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            user.setUsername(oneParameter.getMobile());
            user.setMobile(oneParameter.getMobile());
            userRepository.save(user);
            result.setStatus(1);
            result.setData(user);
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result reset(OneParameter oneParameter) {
        Result result = new Result();
        if (StringUtils.isBlank(oneParameter.getMobile()) || StringUtils.isBlank(oneParameter.getPassword())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        List<User> users = userRepository.findByUsername(oneParameter.getMobile());
        if (users.size() == 0) {
            result.setStatus(9);
            result.setMessage("当前用户不存在!");
        } else {
            User user = users.get(0);
            user.setPassword(oneParameter.getPassword());
            userRepository.save(user);
            result.setStatus(1);
            result.setData(user);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result follow(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || oneParameter.getTouserid() == null || oneParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(oneParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("要关注的用户不存在!");
            } else {
                Follow follow = new Follow();
                follow.setUser(user);
                follow.setTouser(touser);
                follow.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                followRepository.save(follow);
                result.setStatus(1);
                createNotice(touser, user, null, "关注", 2);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result unfollow(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || oneParameter.getTouserid() == null || oneParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(oneParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("要取消关注的用户不存在!");
            } else {
                List<Follow> follows = followRepository.findByUserAndTouser(user, touser);
                follows.forEach(e -> {
                    followRepository.delete(e);
                });
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    public Result followMy(OneParameter oneParameter, Pageable pageable) {
        Result result = new Result();
        if (oneParameter.getTouserid() == null || oneParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User touser = userRepository.findById(oneParameter.getTouserid()).get();
        if (touser == null) {
            result.setMessage("主页用户用户不存在!");
            return null;
        } else {
            Page<Follow> follows = followRepository.findByUser(touser, pageable);
            if (oneParameter.getUserid() != null && oneParameter.getUserid() != 0) {
                User user = userRepository.findById(oneParameter.getUserid()).get();
                if (user != null) {
                    follows.forEach(e -> {
                        List<Follow> follows2 = followRepository.findByUserAndTouser(user, touser);
                        if (follows2.size() > 0) {
                            e.setIsFollow(1);
                        } else {
                            e.setIsFollow(0);
                        }
                    });
                }
            }
            result.setStatus(1);
            result.setData(follows);
        }
        return result;
    }

    @Override
    public Result followMe(OneParameter oneParameter, Pageable pageable) {
        Result result = new Result();
        if (oneParameter.getTouserid() == null || oneParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User touser = userRepository.findById(oneParameter.getTouserid()).get();
        if (touser == null) {
            result.setMessage("主页用户用户不存在!");
            return null;
        } else {
            Page<Follow> follows = followRepository.findByTouser(touser, pageable);
            if (oneParameter.getUserid() != null && oneParameter.getUserid() != 0) {
                User user = userRepository.findById(oneParameter.getUserid()).get();
                if (user != null) {
                    follows.forEach(e -> {
                        List<Follow> follows2 = followRepository.findByUserAndTouser(user, e.getUser());
                        if (follows2.size() > 0) {
                            e.setIsFollow(1);
                        } else {
                            e.setIsFollow(0);
                        }
                    });
                }
            }
            result.setStatus(1);
            result.setData(follows);
        }
        return result;
    }

    @Override
    public Result getSetting(AdminOneParameter adminOneParameter) {
        Result result = new Result();
        if (adminOneParameter.getType() == null || adminOneParameter.getType() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setStatus(1);
        result.setData(settingRepository.findAllByType(adminOneParameter.getType()));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result search(OneParameter oneParameter, Pageable pageable) {
        Result result = new Result();
        if (StringUtils.isBlank(oneParameter.getKeyword())) {
            Page<User> users = userRepository.findAllByIdNot(1, pageable);
            if (oneParameter.getUserid() != null && oneParameter.getUserid() != 0) {
                User user = userRepository.findById(oneParameter.getUserid()).get();
                if (user == null || isNotLogin(user)) {
                    result.setStatus(9);
                    result.setMessage("当前用户不存在或未登录!");
                    return result;
                }
                users.forEach(e -> {
                    List<Follow> follows2 = followRepository.findByUserAndTouser(user, e);
                    if (follows2.size() > 0) {
                        e.setIsFollow(1);
                    } else {
                        e.setIsFollow(0);
                    }
                });
            }
            result.setStatus(1);
            result.setData(users);
        } else {
            Page<User> users = userRepository.findAllByUsernameContainsOrNicknameContainsOrSexContainsOrJobsContains(oneParameter.getKeyword(), oneParameter.getKeyword(), oneParameter.getKeyword(), oneParameter.getKeyword(), pageable);
            if (oneParameter.getUserid() != null && oneParameter.getUserid() != 0) {
                User user = userRepository.findById(oneParameter.getUserid()).get();
                if (user == null || isNotLogin(user)) {
                    result.setStatus(9);
                    result.setMessage("当前用户不存在或未登录!");
                    return result;
                }
                users.forEach(e -> {
                    List<Follow> follows2 = followRepository.findByUserAndTouser(user, e);
                    if (follows2.size() > 0) {
                        e.setIsFollow(1);
                    } else {
                        e.setIsFollow(0);
                    }
                });
                Searching searching = new Searching();
                searching.setKeyword(oneParameter.getKeyword());
                searching.setUser(user);
                searching.setIsclear(0);
                searching.setType(2);
                searching.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                searchingRepository.save(searching);
                result.setStatus(1);
                result.setData(users);
            }
        }
        return result;
    }

    @Override
    public Result refer(OneParameter oneParameter, Pageable pageable) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            result.setStatus(1);
            Page<User> users = userRepository.findByRefer(user.getId(), pageable);
            users.forEach(e -> {
                List<Follow> follows = followRepository.findByUserAndTouser(user, e);
                if (follows.size() > 0) {
                    e.setIsFollow(1);
                } else {
                    e.setIsFollow(0);
                }
            });
            result.setData(users);
        }
        return result;
    }

    @Override
    public Result followIs(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || oneParameter.getTouserid() == null || oneParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(oneParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("要检查关注的用户不存在!");
            } else {
                List<Follow> follows = followRepository.findByUserAndTouser(user, touser);
                if (follows.size() > 0) {
                    result.setStatus(1);
                } else {
                    result.setMessage("你未关注此用户");
                }
            }
        }
        return result;
    }

    @Override
    public Result studiedIs(OneParameter oneParameter) {
        Result result = new Result();
        if (oneParameter.getUserid() == null || oneParameter.getUserid() == 0 || oneParameter.getHelpid() == null || oneParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(oneParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Help help = helpRepository.findById(oneParameter.getHelpid()).get();
            if (help == null) {
                result.setMessage("要检查的求助不存在!");
            } else {
                List<Study> studies = studyRepository.findAllByUserAndHelp(user, help);
                if (studies.size() > 0) {
                    result.setStatus(1);
                } else {
                    result.setMessage("你未想学此求助");
                }
            }
        }
        return result;
    }

    @Override
    public Result uploadImage(MultipartFile multipartFile) {
        Result result = new Result();
        if (multipartFile == null || multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        //处理图片
        try {
            FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isNotLogin(User user) {
        Token token = tokenRepository.findTop1ByUserOrderByCreatetimeDesc(user);
        if (token != null && token.getExpiretime() > new Date().getTime() && token.getOuttime() == null) return false;
        return true;
    }

    public void createNotice(User user, User fromuser, Help help, String message, Integer type) {
        if (user.getId() == fromuser.getId()) return;
        Notice notice = new Notice();
        notice.setUser(user);
        notice.setFromuser(fromuser);
        notice.setHelp(help);
        notice.setType(type);
        notice.setIsread(0);
        notice.setMessage(message);
        notice.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        noticeRepository.save(notice);
    }

    public void createSysNotice(User user, Help help, String message, Integer type, Integer mtype) {
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
        messageRepository.save(me);
    }
}
