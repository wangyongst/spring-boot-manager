package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.AdminUser;
import com.spring.boot.manager.entity.Advert;
import com.spring.boot.manager.entity.Buy;
import com.spring.boot.manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Buy.class, idClass = Integer.class)
public interface BuyRepository extends JpaRepository<Buy, Integer> {
    public List<Buy> findAllByUserAndAdvert(User user, Advert advert);

    public void removeAllByUser(User user);

    @Query("select buy from Buy buy where buy.advert.adminuser = ?1 order by buy.advert.outtime desc ")
    public Page<Buy> findByAdminuser(AdminUser adminuser, Pageable pageable);


    public void deleteAllByAdvert(Advert advert);
}