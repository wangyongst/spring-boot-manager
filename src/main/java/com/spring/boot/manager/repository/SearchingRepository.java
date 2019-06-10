package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Searching;
import com.spring.boot.manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Searching.class, idClass = Integer.class)
public interface SearchingRepository extends JpaRepository<Searching, Integer> {
    public void removeAllByUser(User user);

    @Query("select searching from Searching searching where searching.user = ?1 and searching.type = ?2 and searching.isclear <> ?3 group by searching.keyword")
    public Page<Searching> findByUserAndTypeAndIsclearNot(User user, int type, int isclear, Pageable pageable);

    @Query("select searching from Searching searching where searching.user = ?1 and searching.isclear <> ?2 group by searching.keyword")
    public Page<Searching> findByUserAndIsclearNot(User user, int isclear, Pageable pageable);

    public List<Searching> findByUserAndTypeAndIsclearNot(User user, int type, int isclear);

    public List<Searching> findByUserAndIsclearNot(User user, int isclear);

    @Query("select searching,count (searching) from Searching searching group by searching.keyword,searching.type order by count(searching) desc")
    public List<Object[]> findAllTimes();
}