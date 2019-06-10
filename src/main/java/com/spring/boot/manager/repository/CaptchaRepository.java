package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Captcha.class, idClass = Integer.class)
public interface CaptchaRepository extends JpaRepository<Captcha, Integer> {

    public List<Captcha> findByMobileAndCode(String mobile, String code);

    public List<Captcha> findByMobileAndCreatetimeGreaterThan(String mobile, String createTime);

}