package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Product.class, idClass = Integer.class)
public interface ProductRepository extends JpaRepository<Product, Integer> {

}