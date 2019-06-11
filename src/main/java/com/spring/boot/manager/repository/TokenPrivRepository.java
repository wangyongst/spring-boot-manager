package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Token;
import com.spring.boot.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Token.class, idClass = Integer.class)
public interface TokenPrivRepository extends JpaRepository<Token,Integer> {
}