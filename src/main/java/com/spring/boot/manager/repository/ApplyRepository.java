package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Apply;
import com.spring.boot.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Apply.class, idClass = Integer.class)
public interface ApplyRepository extends JpaRepository<Apply,Integer> {
}