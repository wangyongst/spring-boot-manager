package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Deliver;
import com.spring.boot.manager.entity.Purch;
import com.spring.boot.manager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Deliver.class, idClass = Integer.class)
public interface DeliverRepository extends JpaRepository<Deliver, Integer>, JpaSpecificationExecutor {

    List<Deliver> findByPurch(Purch purch);

    List<Deliver> findByStatus(int status);

    int countByAccepttimeLike(String accepttime);
}