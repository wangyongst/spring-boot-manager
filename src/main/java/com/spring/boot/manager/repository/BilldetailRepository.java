package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Bill;
import com.spring.boot.manager.entity.Billdetail;
import com.spring.boot.manager.entity.Purch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Billdetail.class, idClass = Integer.class)
public interface BilldetailRepository extends JpaRepository<Billdetail, Integer>, JpaSpecificationExecutor {

    List<Billdetail> findByBill(Bill bill);

    List<Billdetail> findByPurch(Purch purch);

    List<Billdetail> findByBillAndStatus(Bill bill, int status);
}