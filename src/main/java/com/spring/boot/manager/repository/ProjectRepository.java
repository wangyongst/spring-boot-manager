package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Project.class, idClass = Integer.class)
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByCustomerLikeAndNameLike(String customer, String name, Sort sort);

    List<Project> findByCustomerLike(String customer, Sort sort);

    List<Project> findByNameLike(String name, Sort sort);

    @Query("select distinct project.customer from Project project")
    List<String> findDistinctCustomer();

    @Query("select distinct project.name from Project project where project.customer = ?1")
    List<String> findDistinctNameByCustomer(String customer);

}