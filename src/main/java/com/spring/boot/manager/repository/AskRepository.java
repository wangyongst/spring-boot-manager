package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Ask.class, idClass = Integer.class)
public interface AskRepository extends JpaRepository<Ask,Integer> , JpaSpecificationExecutor {
}