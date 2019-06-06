package com.spring.boot.service.repository;

import com.spring.boot.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository extends JpaRepository<User,Integer> {
}