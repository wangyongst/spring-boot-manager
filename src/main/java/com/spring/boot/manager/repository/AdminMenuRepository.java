package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = AdminMenu.class, idClass = Integer.class)
public interface AdminMenuRepository extends JpaRepository<AdminMenu, Integer> {
}