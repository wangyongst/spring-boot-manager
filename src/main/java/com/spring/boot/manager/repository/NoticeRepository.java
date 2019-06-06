package com.spring.boot.manager.repository;

import com.myweb.pojo.Help;
import com.myweb.pojo.Notice;
import com.myweb.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Notice.class, idClass = Integer.class)
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    public void removeAllByUser(User user);

    public void removeAllByFromuser(User user);

    public void deleteAllByHelp(Help help);

    public Page<Notice> findAllByUserAndIsreadNot(User user, int isread, Pageable pageable);

    public List<Notice> findAllByUserAndIsreadNot(User user, int isread);
}