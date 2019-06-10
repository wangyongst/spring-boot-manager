package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.User2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = User2.class, idClass = Integer.class)
public interface User2Repository extends JpaRepository<User2, Integer> {
    public Page<User2> findAllByIdNot(Integer id, Pageable pageable);
}