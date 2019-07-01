package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Setting.class, idClass = Integer.class)
public interface SettingRepository extends JpaRepository<Setting,Integer> {

    List<Setting> findByType(int type);
}