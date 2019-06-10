package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.AdminPrivilege;
import com.spring.boot.manager.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = AdminPrivilege.class, idClass = Integer.class)
public interface AdminPrivilegeRepository extends JpaRepository<AdminPrivilege, Integer> {
    public List<AdminPrivilege> findAllByAdminRole(AdminRole adminRole);
}