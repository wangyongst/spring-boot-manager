package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.TwoParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.TwoService;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service("TwoService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class TwoServiceImpl implements TwoService {

    private static final Logger logger = LogManager.getLogger(TwoServiceImpl.class);

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private SearchingRepository searchingRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ReferipsRepository referipsRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result seek(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || twoParameter.getAudience() == null || twoParameter.getAudience() == 0 || twoParameter.getDraft() == null || twoParameter.getDraft() == 0 || StringUtils.isBlank(twoParameter.getImage()) || StringUtils.isBlank(twoParameter.getTag()) || StringUtils.isBlank(twoParameter.getDesign())) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setMessage("当前用户不存在或未登录!");
        } else {
            if (helpRepository.findByUser(user).size() > 0 && userRepository.findByRefer(user.getId()).size() < 1) {
                result.setStatus(2);
                result.setMessage("未邀请过好友注册，不能发布");
                return result;
            }
            Help help = new Help();
            help.setUser(user);
            help.setAudience(twoParameter.getAudience());
            help.setBackground(twoParameter.getBackground());
            help.setTitle(twoParameter.getTitle());
            help.setImage(twoParameter.getImage());
            help.setDescription(twoParameter.getDescription());
            help.setVideo(twoParameter.getVideo());
            help.setTag(twoParameter.getTag());
            help.setDesign(twoParameter.getDesign());
            help.setBackground(twoParameter.getBackground());
            help.setSetting(twoParameter.getSetting());
            help.setDraft(twoParameter.getDraft());
            help.setIndexpic(twoParameter.getIndexpic());
            help.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
            help.setStudied(0);
            help.setReaded(0);
            help.setClicked(0);
            help.setFans(0);
            help.setForwarded(0);
            help.setRecommend(0);
            helpRepository.save(help);
            result.setStatus(1);
            result.setData(help);
            List<Follow> follows2 = followRepository.findAllByTouser(user);
            follows2.forEach(e -> {
                createNotice(e.getUser(), user, help, "发布", 4);
            });
        }
        return result;
    }

    @Override
    public Result user(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setMessage("当前用户不存在或未登录!");
        } else {
            if (twoParameter.getDraft() == null || twoParameter.getDraft() == 0) {
                result.setStatus(1);
                result.setData(helpRepository.findByUser(user, pageable));
            } else if (twoParameter.getDraft() != null && twoParameter.getDraft() != 0 && twoParameter.getDraft() != 5) {
                result.setStatus(1);
                result.setData(helpRepository.findByUserAndDraft(user, twoParameter.getDraft(), pageable));
            } else {
                result.setStatus(1);
                result.setData(helpRepository.findByUserAndAudienceNot(user, 3, pageable));
            }
        }
        return result;
    }

    @Override
    public Result index(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        result.setStatus(1);
        Page<Help> helps = null;
        if (twoParameter.getDesign() == null) {
            helps = helpRepository.findByDraftAndAudience(4, 1, pageable);
        } else {
            helps = helpRepository.findByDesignAndAudienceAndDraft(twoParameter.getDesign(), 1, 4, pageable);
        }
        if (twoParameter.getUserid() != null && twoParameter.getUserid() != 0) {
            User user = userRepository.findById(twoParameter.getUserid()).get();
            if (user == null || isNotLogin(user)) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            }
            helps.forEach(e -> {
                List<Study> studies = studyRepository.findAllByUserAndHelp(user, helpRepository.findById(e.getId()).get());
                if (studies.size() > 0) {
                    e.setIsStudied(1);
                } else {
                    e.setIsStudied(0);
                }
            });
        }
        result.setData(helps);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result advert(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        result.setStatus(1);
        Page<Advert> adverts = advertRepository.findAllByReferNotAndOuttimeGreaterThanOrderByReferDesc(2, new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()), pageable);
        adverts.forEach(e -> {
            e.setExposure(e.getExposure() + 1);
            advertRepository.save(e);
            e.setAdminuser(null);
            if (twoParameter.getUserid() != null && twoParameter.getUserid() != 0) {
                User user = userRepository.findById(twoParameter.getUserid()).get();
                if (user != null) {
                    List<Buy> buys = buyRepository.findAllByUserAndAdvert(user, e);
                    if (buys.size() > 0) {
                        e.setIsBuy(1);
                    } else {
                        e.setIsBuy(0);
                    }
                }
            }
        });
        result.setData(adverts);
        return result;
    }


    @Override
    public Result advertUser(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        result.setStatus(1);
        Page<User> users = userRepository.findAllByRefertimeGreaterThanOrderByReferDesc(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()), pageable);

        if (twoParameter.getUserid() != null && twoParameter.getUserid() != 0) {
            User user = userRepository.findById(twoParameter.getUserid()).get();
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
        result.setData(users);
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result search(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        result.setStatus(1);
        Page<Help> helps = null;
        if (StringUtils.isBlank(twoParameter.getTag())) {
            helps = helpRepository.findByDraftAndAudience(4, 1, pageable);
        } else {
            helps = helpRepository.findByDraftAndAudienceAndTagContains(4, 1, "%" + twoParameter.getTag() + "%", pageable);
        }
        if (twoParameter.getUserid() != null && twoParameter.getUserid() != 0) {
            User user = userRepository.findById(twoParameter.getUserid()).get();
            if (user == null || isNotLogin(user)) {
                result.setStatus(9);
                result.setMessage("当前用户不存在或未登录!");
                return result;
            }
            helps.forEach(e -> {
                List<Study> studies = studyRepository.findAllByUserAndHelp(user, helpRepository.findById(e.getId()).get());
                if (studies.size() > 0) {
                    e.setIsStudied(1);
                } else {
                    e.setIsStudied(0);
                }
            });
        }
        result.setData(helps);
        return result;
    }

    @Override
    public Result mine(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        if (twoParameter.getTouserid() == null || twoParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getTouserid()).get();
        if (user == null) {
            result.setStatus(0);
            result.setMessage("主页用户不存在!");
        } else {
            if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0) {
                if (twoParameter.getType() == null || twoParameter.getType() == 0) {
                    if (StringUtils.isBlank(twoParameter.getTag())) {
                        result.setData(helpRepository.findByUserAndDraftAndAudience(user, 4, 1, pageable));
                    } else {
                        result.setData(helpRepository.findByUserAndAudienceAndDraftAndTagContains(user, 1, 4, twoParameter.getTag(), pageable));
                    }
                } else {
                    if (StringUtils.isBlank(twoParameter.getTag())) {
                        result.setData(studyRepository.queryAllByUser(user, pageable));
                    } else {
                        result.setData(studyRepository.queryAllByUserAndTagLike(user, "%" + twoParameter.getTag() + "%", pageable));
                    }
                }
                result.setStatus(1);
            } else {
                User user1 = userRepository.findById(twoParameter.getUserid()).get();
                if (user1 == null || isNotLogin(user1)) {
                    result.setStatus(0);
                    result.setMessage("当前用户不存在或未登录!");
                    return result;
                }
                if (twoParameter.getType() == null || twoParameter.getType() == 0) {
                    if (StringUtils.isBlank(twoParameter.getTag())) {
                        result.setData(helpRepository.queryAllByMine(user, user1, pageable));
                    } else {
                        result.setData(helpRepository.queryAllByTagMine(user, user1, "%" + twoParameter.getTag() + "%", pageable));
                    }
                } else {
                    if (StringUtils.isBlank(twoParameter.getTag())) {
                        result.setData(studyRepository.queryAllByUser(user, pageable));
                    } else {
                        result.setData(studyRepository.queryAllByUserAndTagLike(user, twoParameter.getTag(), pageable));
                    }
                }
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    public Result searchingUser(TwoParameter twoParameter, Pageable pageable) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null) {
            result.setStatus(0);
            result.setMessage("当前用户不存在0!");
        } else {
            result.setStatus(1);
            if (twoParameter.getType() == null || twoParameter.getType() == 0)
                result.setData(searchingRepository.findByUserAndIsclearNot(user, 1, pageable));
            else
                result.setData(searchingRepository.findByUserAndTypeAndIsclearNot(user, twoParameter.getType(), 1, pageable));
        }
        return result;
    }

    @Override
    public Result info(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getTouserid() == null || twoParameter.getTouserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getTouserid()).get();
        if (user == null) {
            result.setStatus(9);
            result.setMessage("当前用户不存!");
        } else {
            user.setPublished(helpRepository.countAllByUser(user));
            user.setFollowed(followRepository.countAllByUser(user));
            user.setFans(followRepository.countAllByTouser(user));
            if (twoParameter.getUserid() != null && twoParameter.getUserid() != 0) {
                User u = userRepository.findById(twoParameter.getUserid()).get();
                if (u == null || isNotLogin(u)) {
                    result.setStatus(9);
                    result.setMessage("当前用户不存在或未登录!");
                    return result;
                } else {
                    List<Follow> follows2 = followRepository.findByUserAndTouser(u, user);
                    if (follows2.size() > 0) {
                        user.setIsFollow(1);
                    } else {
                        user.setIsFollow(0);
                    }
                }
            }
            result.setStatus(1);
            result.setData(user);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result delete(TwoParameter twoParameter, String helpids) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || StringUtils.isBlank(helpids)) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            String[] ids = helpids.split(",");
            for (String id : ids) {
                if (StringUtils.isNotBlank(id)) {
                    Help help = helpRepository.findById(Integer.parseInt(id)).get();
                    studyRepository.deleteAllByHelp(help);
                    reportRepository.deleteAllByHelp(help);
                    referipsRepository.deleteAllByHelp(help);
                    noticeRepository.deleteAllByHelp(help);
                    messageRepository.removeAllByHelp(help);
                    helpRepository.delete(help);
                }
            }
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result click(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getAdvertid() == null || twoParameter.getAdvertid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        Advert advert = advertRepository.findById(twoParameter.getAdvertid()).get();
        if (advert == null) {
            result.setMessage("广告不存在!");
        } else {
            advert.setClicked(advert.getClicked() + 1);
            advertRepository.save(advert);
            result.setStatus(1);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result buy(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || twoParameter.getAdvertid() == null || twoParameter.getAdvertid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Advert advert = advertRepository.findById(twoParameter.getAdvertid()).get();
            if (advert == null) {
                result.setMessage("广告不存在!");
            } else {
                advert.setBuy(advert.getBuy() + 1);
                advertRepository.save(advert);
                Buy buy = new Buy();
                buy.setUser(user);
                buy.setAdvert(advert);
                buy.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                buyRepository.save(buy);
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result searchingClear(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            if (twoParameter.getType() == null || twoParameter.getType() == 0) {
                List<Searching> searchings = searchingRepository.findByUserAndIsclearNot(user, 1);
                searchings.forEach(e -> {
                    e.setIsclear(1);
                    searchingRepository.save(e);
                });
            } else {
                List<Searching> searchings = searchingRepository.findByUserAndTypeAndIsclearNot(user, twoParameter.getType(), 1);
                searchings.forEach(e -> {
                    e.setIsclear(1);
                    searchingRepository.save(e);
                });
            }
        }
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result searchingClearId(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || twoParameter.getSearchingid() == null || twoParameter.getSearchingid() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Searching searching = searchingRepository.findById(twoParameter.getSearchingid()).get();
            if (searching == null) {
                result.setMessage("要删除的历史记录不存在!");
            } else {
                result.setStatus(1);
                searchingRepository.delete(searching);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result hidden(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || twoParameter.getHelpid() == null || twoParameter.getHelpid() == 0 || twoParameter.getType() == null || twoParameter.getType() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null || isNotLogin(user)) {
            result.setStatus(9);
            result.setMessage("当前用户不存在或未登录!");
        } else {
            Help help = helpRepository.findById(twoParameter.getHelpid()).get();
            if (help == null) {
                result.setMessage("选择的求助不存在!");
            } else if (twoParameter.getType() == 1) {
                help.setAudience(3);
                helpRepository.save(help);
                result.setStatus(1);
            } else if (twoParameter.getType() == 2) {
                help.setAudience(1);
                helpRepository.save(help);
                result.setStatus(1);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public Result report(TwoParameter twoParameter) {
        Result result = new Result();
        if (twoParameter.getUserid() == null || twoParameter.getUserid() == 0 || twoParameter.getType() == null || twoParameter.getType() == 0) {
            result.setMessage("必须的参数不能为空!");
            return result;
        }
        User user = userRepository.findById(twoParameter.getUserid()).get();
        if (user == null) {
            result.setStatus(9);
            result.setMessage("当前用户不存在!");
        } else {
            if (twoParameter.getType() == 1) {
                if (twoParameter.getHelpid() == null || twoParameter.getHelpid() == 0) {
                    result.setMessage("必须的参数不能为空!");
                    return result;
                }
                Help help = helpRepository.findById(twoParameter.getHelpid()).get();
                if (help == null) {
                    result.setMessage("选择的求助不存在!");
                    return result;
                }
                Report report = new Report();
                report.setHelp(help);
                report.setUser(user);
                report.setIsdone(0);
                report.setTitle(twoParameter.getTitle());
                report.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                reportRepository.save(report);
                result.setStatus(1);
            } else if (twoParameter.getType() == 2) {
                if (twoParameter.getTouserid() == null || twoParameter.getTouserid() == 0) {
                    result.setMessage("必须的参数不能为空!");
                    return result;
                }
                User touser = userRepository.findById(twoParameter.getTouserid()).get();
                if (touser == null) {
                    result.setMessage("选择的求助不存在!");
                    return result;
                }
                Report report = new Report();
                report.setTouser(touser);
                report.setUser(user);
                report.setIsdone(0);
                report.setTitle(twoParameter.getTitle());
                report.setCreatetime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
                reportRepository.save(report);
                result.setStatus(1);
            }
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
