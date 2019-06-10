package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminOneParameter;
import com.spring.boot.manager.model.FourParameter;
import com.spring.boot.manager.model.OneParameter;
import com.spring.boot.manager.model.TwoParameter;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

public interface AdminOneService {
    public Result login(OneParameter oneParameter, HttpSession httpSession);

    public Result userMe(HttpSession httpSession);

    public Result userAdminList(HttpSession httpSession);

    public Result userAdmin(FourParameter fourParameter, HttpSession httpSession);

    public Result postUserAdmin(FourParameter fourParameter, HttpSession httpSession);

    public Result userRoleList(HttpSession httpSession);

    public Result userRole(FourParameter fourParameter, HttpSession httpSession);

    public Result postUserRole(FourParameter fourParameter, HttpSession httpSession);

    public Result userPrivilege(FourParameter fourParameter, HttpSession httpSession);

    public Result postUserPrivilege(FourParameter fourParameter, HttpSession httpSession);

    public Result userMenuList(HttpSession httpSession);

    public Result studyHelpList(OneParameter oneParameter, HttpSession httpSession);

    public Result userSendMessage(OneParameter oneParameter, HttpSession httpSession);

    public Result userRole15(HttpSession httpSession);

    public Result searchingList(HttpSession httpSession);

    public Result userGroupList(HttpSession httpSession);

    public Result reportListUser(HttpSession httpSession);

    public Result reportListHelp(HttpSession httpSession);

    public Result logout(HttpSession httpSession);

    public Result userList(AdminOneParameter adminOneParameter, HttpSession httpSession);

    public Result showlog(HttpSession httpSession);

    public Result userTokenList(HttpSession httpSession);

    public Result user(OneParameter oneParameter, HttpSession httpSession) throws ParseException;

    public Result userLock(OneParameter oneParameter, HttpSession httpSession);

    public Result getUser(OneParameter oneParameter, HttpSession httpSession);

    public Result studyCount(HttpSession httpSession);

    public Result countUser(HttpSession httpSession);

    public Result messageList(HttpSession httpSession);

    public Result countMessageUser(OneParameter oneParameter, HttpSession httpSession);

    public Result countFollow(OneParameter oneParameter, HttpSession httpSession);

    public Result follow(OneParameter oneParameter, Pageable pageable, HttpSession httpSession);

    public Result countToken(HttpSession httpSession);

    public Result helpDraft(HttpSession httpSession);

    public Result help(TwoParameter twoParameter, HttpSession httpSession);

    public Result helpList(HttpSession httpSession);

    public Result advertList(HttpSession httpSession);

    public Result postHelp(TwoParameter twoParameter, HttpSession httpSession);

    public Result setting(AdminOneParameter adminOneParameter, HttpSession httpSession);

    public Result postAdvert(AdminOneParameter adminOneParameter, HttpSession httpSession);

    public Result advert(TwoParameter twoParameter, HttpSession httpSession);

    public Result getSetting(AdminOneParameter adminOneParameter, HttpSession httpSession);

    public Result settingList(HttpSession httpSession);

    public Result helpRefer(TwoParameter twoParameter, HttpSession httpSession);

    public Result advertRefer(TwoParameter twoParameter, HttpSession httpSession);

    public Result advertOut(TwoParameter twoParameter, HttpSession httpSession);
}
