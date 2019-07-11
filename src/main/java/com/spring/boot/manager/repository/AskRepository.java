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

    List<Ask> findAllByTypeNot(int type);

    List<Ask> findAllByRequest(Request request);

    List<Ask> findByTypeAndCreatetimeLessThanEqualAndConfirmtimeIsNull(int type, String time);

    List<Ask> findByTypeAndConfirmtimeLessThanEqual(int type, String time);

}