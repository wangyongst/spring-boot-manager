package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.Count;
import com.spring.boot.manager.model.ThreeParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.ThreeService;
import com.spring.boot.manager.utils.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


@Service("ThreeService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class ThreeServiceImpl implements ThreeService {

    private static final Logger logger = LogManager.getLogger(ThreeServiceImpl.class);

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimenewRepository timenewRepository;


    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ReferipsRepository referipsRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result click(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("点击的求助不存在");
        } else {
            help.setClicked(help.getClicked() + 1);
            helpRepository.save(help);
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result forward(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("转发的求助不存在");
        } else {
            help.setForwarded(help.getForwarded() + 1);
            helpRepository.save(help);
            result.setStatus(1);
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result advertInfo(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getAdvertid() == null || threeParameter.getAdvertid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setStatus(1);
        Advert advert = advertRepository.findById(threeParameter.getAdvertid()).get();
        if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
            List<Buy> buyList = buyRepository.findAllByUserAndAdvert(userRepository.findById(threeParameter.getUserid()).get(), advert);
            if (buyList.size() > 0) {
                advert.setIsBuy(1);
            }
        }
        result.setData(advert);
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result advertForward(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getAdvertid() == null || threeParameter.getAdvertid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Advert advert = advertRepository.findById(threeParameter.getAdvertid()).get();
        if (advert == null) {
            result.setMessage("转发的广告不存在");
        } else {
            advert.setForwarded(advert.getForwarded() + 1);
            advertRepository.save(advert);
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result helpRead(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("阅读的求助不存在");
        } else {
            help.setReaded(help.getReaded() + 1);
            helpRepository.save(help);
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result recommend(ThreeParameter threeParameter, HttpServletRequest request) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0 || threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("推荐的求助不存在");
        } else {
            User user = userRepository.findById(threeParameter.getUserid()).get();
            if (user == null) {
                result.setMessage("发送用户不存在!");
            } else {
                Referips referips = referipsRepository.findTop1ByUserAndHelpAndIp(user, help, getIp(request));
                if (referips == null) {
                    referips = new Referips();
                    referips.setUser(user);
                    referips.setHelp(help);
                    referips.setIp(getIp(request));
                    referipsRepository.save(referips);
                } else {
                    result.setMessage("当前IP已经推荐过，不能再推荐!");
                    return result;
                }
                help.setRecommend(help.getRecommend() + 1);
                helpRepository.save(help);
                if (help.getRecommend() == 2000) createSysNotice(help.getUser(), help, "恭喜你，你想学的效果被推荐过2000次", 5, 1);
                result.setStatus(1);
            }
        }
        return result;
    }

    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result study(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0 || threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("想要学习的求助不存在");
        } else {
            if (threeParameter.getType() == null || threeParameter.getType() == 0) {
                Study study = new Study();
                study.setUser(user);
                study.setHelp(help);
                study.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                studyRepository.save(study);
                help.setStudied(help.getStudied() + 1);
                helpRepository.save(help);
                result.setStatus(1);
                createNotice(help.getUser(), user, help, "想学", 1);
            } else {
                studyRepository.deleteAllByHelpAndUser(help, user);
                if (help.getStudied() > 1) {
                    help.setStudied(help.getStudied() - 1);
                    helpRepository.save(help);
                }
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result send(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getTouserid() == null || threeParameter.getTouserid() == 0 || StringUtils.isBlank(threeParameter.getMessage())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Message message = new Message();
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(threeParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("发送用户不存在!");
            } else {
                message.setUser(user);
                message.setTouser(touser);
                message.setMessage(threeParameter.getMessage());
                message.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                message.setIsread(0);
                messageRepository.save(message);
                result.setStatus(1);
                result.setData(message);
            }
        }
        return result;
    }

    @Override
    public Result user(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            result.setStatus(1);
            List<User> userList = messageRepository.findUserByUserOrTouser(user);
            userList.addAll(messageRepository.findTouserByUserOrTouser(user));
            HashSet h = new HashSet(userList);
            userList.clear();
            userList.addAll(h);
            userList.remove(user);
            userList.forEach(e -> {
                e.setNewTime(messageRepository.findTop1ByUserOrTouserOrderByCreatetimeDesc(user, user).getCreatetime());
            });
            result.setData(userList);
        }
        return result;
    }

    @Override
    public Result userMost(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        Page<User> users = helpRepository.findUserByMost(pageable);
        users.forEach(e -> {
            e.setPublished(helpRepository.countAllByUserAndAudience(e, 1));
            e.setFollowed(followRepository.countAllByUser(e));
            e.setFans(followRepository.countAllByTouser(e));
            if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
                User u = userRepository.findById(threeParameter.getUserid()).get();
                if (u == null || isNotLogin(u)) {
                    result.setStatus(9);
                    result.setMessage("当前用户不存在或未登录!");
                } else {
                    List<Follow> follows2 = followRepository.findByUserAndTouser(u, e);
                    if (follows2.size() > 0) {
                        e.setIsFollow(1);
                    } else {
                        e.setIsFollow(0);
                    }
                }
            }
        });
        if (result.getStatus() == 9) return result;
        result.setStatus(1);
        result.setData(users);
        return result;
    }


    @Override
    public Result advertStudiedList(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getAdminuserid() != null && threeParameter.getAdminuserid() != 0 && threeParameter.getAdvertid() != null && threeParameter.getAdvertid() != 0) {
            AdminUser u = adminUserRepository.findById(threeParameter.getAdminuserid()).get();
            if (u == null) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            } else {
                Page<Buy> adverts = buyRepository.findByAdminuser(u, pageable);
                result.setStatus(1);
                result.setData(adverts);
            }
        } else {
            result.setMessage("必须的参数不能为空!");
        }
        return result;
    }


    @Override
    public Result advertList(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getAdminuserid() != null && threeParameter.getAdminuserid() != 0 || threeParameter.getType() == null || threeParameter.getType() == 0) {
            AdminUser u = adminUserRepository.findById(threeParameter.getAdminuserid()).get();
            if (u == null) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            } else {
                if (threeParameter.getType() != null && threeParameter.getType() == 4) {
                    result.setData(userRepository.findByUsername(u.getUsername()));
                    return result;
                }
                if (threeParameter.getType() != null && threeParameter.getType() == 5) {
                    result.setData(messageRepository.findByAdminuser(u.getId()));
                    return result;
                }
                Page<Advert> adverts = advertRepository.findByAdminuserAndTypeOrderByOuttimeDesc(u, threeParameter.getType(), pageable);
                adverts.forEach(e -> {
                    try {
                        if (e.getRefer() != null && e.getRefer() == 2) e.setStatus(1);
                        else if (new SimpleDateFormat("yyyy-MM-dd").parse(e.getOuttime()).getTime() < new Date().getTime())
                            e.setStatus(3);
                        else e.setStatus(2);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                });
                result.setStatus(1);
                result.setData(adverts);
            }
        } else {
            result.setMessage("必须的参数不能为空!");
        }
        return result;
    }


    @Override
    public Result userMostHelp(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        Page<User> users = helpRepository.findUserByMost(pageable);
        if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
            User u = userRepository.findById(threeParameter.getUserid()).get();
            if (u == null || isNotLogin(u)) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            }
        }
        users.forEach(e -> {
            e.setPublished(helpRepository.countAllByUser(e));
            e.setFollowed(followRepository.countAllByUser(e));
            e.setFans(followRepository.countAllByTouser(e));
            if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
                User u = userRepository.findById(threeParameter.getUserid()).get();
                List<Follow> follows2 = followRepository.findByUserAndTouser(u, e);
                if (follows2.size() > 0) {
                    e.setIsFollow(1);
                } else {
                    e.setIsFollow(0);
                }
            }
            List<Help> helps = helpRepository.findTop3ByUserAndAudienceOrderByStudiedDesc(e,1);
            helps.forEach(t -> {
                if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
                    User u = userRepository.findById(threeParameter.getUserid()).get();
                    List<Study> studies = studyRepository.findAllByUserAndHelp(u, t);
                    if (studies.size() > 0) {
                        t.setIsStudied(1);
                    } else {
                        t.setIsStudied(0);
                    }
                }
                t.setUser(null);
            });
            e.setHelps(helps);
        });
        result.setStatus(1);
        result.setData(users);
        return result;
    }

    @Override
    public Result message(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getTouserid() == null || threeParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(threeParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("发送用户不存在!");
            } else {
                result.setStatus(1);
                result.setData(messageRepository.QueryByUserAndTouser(user, touser, pageable));
            }
        }
        return result;
    }

    @Override
    public Result help(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Help help = helpRepository.findById(threeParameter.getHelpid()).get();
        if (help == null) {
            result.setMessage("未找到求助");
            return result;
        }
        result.setStatus(1);
        if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
            User user = userRepository.findById(threeParameter.getUserid()).get();
            if (user == null || isNotLogin(user)) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            }
            List<Study> studies = studyRepository.findAllByUserAndHelp(user, help);
            if (studies.size() > 0) {
                help.setIsStudied(1);
            } else {
                help.setIsStudied(0);
            }
        }
        result.setData(help);
        return result;
    }

    @Override
    public Result helpStudied(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getHelpid() == null || threeParameter.getHelpid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setStatus(1);
        Page<Study> studies = studyRepository.findAllByHelp(helpRepository.findById(threeParameter.getHelpid()).get(), pageable);
        if (threeParameter.getUserid() != null && threeParameter.getUserid() != 0) {
            User user = userRepository.findById(threeParameter.getUserid()).get();
            if (user != null) {
                studies.forEach(e -> {
                    List<Follow> follows2 = followRepository.findByUserAndTouser(user, e.getUser());
                    if (follows2.size() > 0) {
                        e.getUser().setIsFollow(1);
                    } else {
                        e.getUser().setIsFollow(0);
                    }
                });
            }
        }
        result.setData(studies);
        return result;
    }


    @Override
    public Result advertReferList(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getAdminuserid() == null || threeParameter.getAdminuserid() == 0 || threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setStatus(1);
        result.setData(followRepository.findAllByTouser(userRepository.findById(threeParameter.getUserid()).get()));
        return result;
    }


    @Override
    public Result advertMessageList(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getAdminuserid() == null || threeParameter.getAdminuserid() == 0 || threeParameter.getMessageid() == null || threeParameter.getMessageid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        result.setStatus(1);
        Message message = messageRepository.findById(threeParameter.getMessageid()).get();
        result.setData(messageRepository.findByAdminuserAndMessage(threeParameter.getAdminuserid(), message.getMessage(),pageable));
        return result;
    }

    @Override
    public Result noticeNew(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            result.setStatus(1);
            Page<Notice> notices = noticeRepository.findAllByUserAndIsreadNot(user, 1, pageable);
            if (notices.getTotalPages() == 0) {
                result.setStatus(2);
                result.setData(user);
            } else {
                result.setData(notices);
            }
        }
        return result;
    }

    @Override
    public Result followHelp(ThreeParameter threeParameter, Pageable pageable) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            result.setStatus(1);
            Page<Help> helps = helpRepository.queryByFollow(user, pageable);
            helps.forEach(e -> {
                List<Study> studies = studyRepository.findAllByUserAndHelp(user, helpRepository.findById(e.getId()).get());
                if (studies.size() > 0) {
                    e.setIsStudied(1);
                } else {
                    e.setIsStudied(0);
                }
            });
            result.setData(helps);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result read(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getTouserid() == null || threeParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(threeParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("发送用户不存在!");
            } else {
                List<Message> messageList = messageRepository.findByUserOrTouser(user, touser);
                messageList.forEach(e -> {
                    e.setIsread(1);
                    messageRepository.save(e);
                });
                result.setStatus(1);
            }
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result noticeNewRead(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            List<Notice> notices = noticeRepository.findAllByUserAndIsreadNot(user, 1);
            notices.forEach(e -> {
                e.setIsread(1);
                noticeRepository.save(e);
            });
        }
        result.setStatus(1);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result userDelete(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getTouserid() == null || threeParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            User touser = userRepository.findById(threeParameter.getTouserid()).get();
            if (touser == null) {
                result.setMessage("消息用户不存在!");
            } else {
                messageRepository.deleteAllByUserAndTouser(user, touser);
                messageRepository.deleteAllByUserAndTouser(touser, user);
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result messageDelete(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getMessageid() == null || threeParameter.getMessageid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            messageRepository.deleteById(threeParameter.getMessageid());
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result newsCount(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Count count = new Count();
            for (int i = 1; i <= 5; i++) {
                Timenew timenew = new Timenew();
                List<Timenew> timenews = timenewRepository.findByUserAndType(user, i);
                if (timenews.size() == 0) {
                    timenew.setUser(user);
                    timenew.setType(i);
                    timenew.setNewtime("2018年01月01日 00:00:00");
                    timenewRepository.save(timenew);
                } else {
                    timenew = timenews.get(0);
                }
                switch (i) {
                    case 1:
                        count.setHelps(helpRepository.countByFollow(user, timenew.getNewtime()));
                        break;
                    case 2:
                        count.setFollows(followRepository.countAllByUserAndCreatetimeGreaterThan(user, timenew.getNewtime()));
                        break;
                    case 3:
                        count.setFans(followRepository.countAllByTouserAndCreatetimeGreaterThan(user, timenew.getNewtime()));
                        break;
                    case 4:
                        count.setUsers(userRepository.countAllByReferAndCreatetimeGreaterThan(user.getId(), timenew.getNewtime()));
                        break;
                    case 5:
                        count.setMessages(messageRepository.countAllByUserAndCreatetimeGreaterThan(user, timenew.getNewtime()) + messageRepository.countAllByTouserAndCreatetimeGreaterThan(user, timenew.getNewtime()));
                        break;
                }
            }
            result.setStatus(1);
            result.setData(count);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result newsCountRead(ThreeParameter threeParameter) {
        Result result = new Result();
        if (threeParameter.getUserid() == null || threeParameter.getUserid() == 0 || threeParameter.getType() == null || threeParameter.getType() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(threeParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            List<Timenew> timenews = timenewRepository.findByUserAndType(user, threeParameter.getType());
            if (timenews.size() == 0) {
                Timenew timenew = new Timenew();
                timenew.setUser(user);
                timenew.setType(threeParameter.getType());
                timenew.setNewtime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                timenewRepository.save(timenew);
            } else {
                Timenew timenew = timenews.get(0);
                timenew.setNewtime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                timenewRepository.save(timenew);
            }
            result.setStatus(1);
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
