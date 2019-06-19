package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Request.class, idClass = Integer.class)
public interface RequestRepository extends JpaRepository<Request, Integer> {
}