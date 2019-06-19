package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Role.class, idClass = Integer.class)
public interface RoleRepository extends JpaRepository<Role,Integer> {
}