package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Supplier.class, idClass = Integer.class)
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByNameContains(String name);


    List<Supplier> findByName(String name);
}