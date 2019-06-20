package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Resource.class, idClass = Integer.class)
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    @Query("from Resource resource where resource.material.name = ?1")
    List<Resource> findByMaterialName(String name);

    @Query("from Resource resource where resource.project.name = ?1")
    List<Resource> findByProjectName(String name);

    @Query("from Resource resource where resource.material.name = ?1 and resource.project.name = ?2")
    List<Resource> findByMaterialNameAndProjectName(String name, String name2);
}