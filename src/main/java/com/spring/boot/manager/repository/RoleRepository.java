package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Role;
import com.spring.boot.manager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Role.class, idClass = Integer.class)
public interface RoleRepository extends JpaRepository<Role, Integer> {
}