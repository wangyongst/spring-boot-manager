package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Purch;
import com.spring.boot.manager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Purch.class, idClass = Integer.class)
public interface PurchRepository extends JpaRepository<Purch, Integer>, JpaSpecificationExecutor {

    List<Purch> findAllByAsk(Ask ask);

    List<Purch> findAllBySupplierAndStatus(Supplier suppliler, int status);

    List<Purch> findAllByStatus(int status);

    Purch findTop1ByAskAndAcceptpriceIsNotNullOrderByAcceptpriceAsc(Ask ask);
}