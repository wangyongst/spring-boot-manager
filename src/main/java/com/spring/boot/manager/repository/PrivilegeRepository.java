package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Privilege.class, idClass = Integer.class)
public interface PrivilegeRepository extends JpaRepository<Privilege,Integer> {
}