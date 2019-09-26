package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Request;
import com.spring.boot.manager.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Request.class, idClass = Integer.class)
public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor {

    List<Request> findAllByResource(Resource resource);

    Request findTop1ByNumberLikeOrderByNumberDesc(String number);
}