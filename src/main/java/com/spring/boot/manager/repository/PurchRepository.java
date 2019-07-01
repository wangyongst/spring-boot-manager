package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Purch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Purch.class, idClass = Integer.class)
public interface PurchRepository extends JpaRepository<Purch, Integer>, JpaSpecificationExecutor {
}