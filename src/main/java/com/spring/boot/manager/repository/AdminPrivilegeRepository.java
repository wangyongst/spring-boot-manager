package com.spring.boot.manager.repository;

import com.myweb.pojo.AdminPrivilege;
import com.myweb.pojo.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = AdminPrivilege.class, idClass = Integer.class)
public interface AdminPrivilegeRepository extends JpaRepository<AdminPrivilege, Integer> {
    public List<AdminPrivilege> findAllByAdminRole(AdminRole adminRole);
}