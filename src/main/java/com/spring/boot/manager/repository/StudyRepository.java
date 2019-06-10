package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Help;
import com.spring.boot.manager.entity.Study;
import com.spring.boot.manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Study.class, idClass = Integer.class)
public interface StudyRepository extends JpaRepository<Study, Integer> {

    public void removeAllByUser(User user);

    public int deleteAllByHelp(Help help);

    public int deleteAllByHelpAndUser(Help help, User user);

    public List<Study> findAllByUserAndHelp(User user, Help help);

    @Query("select study.help from Study study where study.user = ?1 group by study.help")
    public Page<Help> queryAllByUser(User user, Pageable pageable);

    @Query("select study.help from Study study where study.user = ?1 and study.help.tag like ?2 group by study.help")
    public Page<Help> queryAllByUserAndTagLike(User user, String tag, Pageable pageable);

    @Query("select study.user from Study study where study.help = ?1")
    public List<User> queryAllByHelp(Help help);

    public Page<Study> findAllByHelp(Help help, Pageable pageable);
}