package com.spring.boot.manager.service;


import com.spring.boot.manager.model.AdminOneParameter;
import com.spring.boot.manager.model.OneParameter;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface OneService {

    public Result regist(OneParameter oneParameter);

    public Result login(OneParameter oneParameter);

    public Result sendCaptcha(OneParameter oneParameter);

    public Result checkCaptcha(OneParameter oneParameter);

    public Result logout(OneParameter oneParameter);

    public Result setBasic(OneParameter oneParameter);

    public Result setAvatar(OneParameter oneParameter);

    public Result setPassword(OneParameter oneParameter);

    public Result setEmail(OneParameter oneParameter);

    public Result reset(OneParameter oneParameter);

    public Result followMy(OneParameter oneParameter, Pageable pageable);

    public Result refer(OneParameter oneParameter, Pageable pageable);

    public Result getSetting(AdminOneParameter adminOneParameter);

    public Result followMe(OneParameter oneParameter, Pageable pageable);

    public Result follow(OneParameter oneParameter);

    public Result destroy(OneParameter oneParameter);

    public Result advertLogin(OneParameter oneParameter);

    public Result advertLogout(OneParameter oneParameter);

    public Result unfollow(OneParameter oneParameter);

    public Result studiedIs(OneParameter oneParameter);

    public Result followIs(OneParameter oneParameter);

    public Result search(OneParameter oneParameter, Pageable pageable);

    public Result uploadImage(MultipartFile multipartFile);

    public Result weixinCode(String code);
}
