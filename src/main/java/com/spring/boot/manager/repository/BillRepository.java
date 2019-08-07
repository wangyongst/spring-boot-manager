package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Bill;
import com.spring.boot.manager.entity.Request;
import com.spring.boot.manager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Bill.class, idClass = Integer.class)
public interface BillRepository extends JpaRepository<Bill, Integer>, JpaSpecificationExecutor {

    List<Bill> findBySupplier(Supplier supplier);

    List<Bill> findBySupplierAndBilltime(Supplier supplier, String billtime);

    @Query("select distinct bill.billtime from Bill bill")
    List<String> findDistinctBillTime();

    List<Bill> findByCreatetimeLike(String createtime);
}