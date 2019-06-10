package com.spring.boot.manager.repository;
import com.spring.boot.manager.entity.AdminRole;
import com.spring.boot.manager.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = AdminUser.class, idClass = Integer.class)
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {
    public List<AdminUser> findByUsernameAndPassword(String username, String password);

    public List<AdminUser> findAllByAdminRole(AdminRole adminRole);
}