package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.AdminUser;
import com.spring.boot.manager.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Advert.class, idClass = Integer.class)
public interface AdvertRepository extends JpaRepository<Advert, Integer> {
    Page<Advert> findAllByReferNotAndOuttimeGreaterThanOrderByReferDesc(Integer refer, String outtime, Pageable pageable);

    public void deleteAllByAdminuser(AdminUser adminUser);

    public Page<Advert> findByAdminuserAndTypeOrderByOuttimeDesc(AdminUser adminUser, Integer type, Pageable pageable);
}