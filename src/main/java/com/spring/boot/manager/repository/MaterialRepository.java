package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Material.class, idClass = Integer.class)
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("select distinct material.code from Material material")
    List<String> findDistinctCode();

    @Query("select distinct material.name from Material material")
    List<String> findDistinctName();

    List<Material> findByCode(String code);
}