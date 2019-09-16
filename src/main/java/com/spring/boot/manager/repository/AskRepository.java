package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Ask.class, idClass = Integer.class)
public interface AskRepository extends JpaRepository<Ask, Integer>, JpaSpecificationExecutor {

    List<Ask> findByStatusAndConfirmtimeLessThanEqual(int status, String time);

    List<Ask> findByStatusAndConfirmtimeIsNull(int status);

    List<Ask> findAllByRequest(Request request);

    List<Ask> findByStatusAndCreatetimeLessThanEqualAndConfirmtimeIsNull(int status, String time);

    List<Ask> findByStatusAndCreatetimeLessThanEqual(int status, String time);

    long countByCreatetimeLike(String createtime);

}