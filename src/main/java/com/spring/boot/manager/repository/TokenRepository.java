package com.spring.boot.manager.repository;

import com.myweb.pojo.Token;
import com.myweb.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Token.class, idClass = Integer.class)
public interface TokenRepository extends JpaRepository<Token, Integer> {
    public Token findTop1ByUserOrderByCreatetimeDesc(User user);

    public int countAllByExpiretimeGreaterThanAndOuttimeIsNull(Long expiretime);

    public void removeAllByUser(User user);

    public List<Token> findAllByOrderByCreatetimeDesc();
}