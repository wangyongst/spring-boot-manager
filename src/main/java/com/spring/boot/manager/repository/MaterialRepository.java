package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Apply;
import com.spring.boot.manager.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Material.class, idClass = Integer.class)
public interface MaterialRepository extends JpaRepository<Material,Integer> {
}