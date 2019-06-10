package com.spring.boot.manager.repository;
import com.spring.boot.manager.entity.AdminLog;
import com.spring.boot.manager.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = AdminLog.class, idClass = Integer.class)
public interface AdminLogRepository extends JpaRepository<AdminLog, Integer> {

    public void deleteAllByAdminUser(AdminUser adminUser);
}