package com.spring.boot.manager.repository;

import com.myweb.pojo.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = AdminRole.class, idClass = Integer.class)
public interface AdminRoleRepository extends JpaRepository<AdminRole, Integer> {
}