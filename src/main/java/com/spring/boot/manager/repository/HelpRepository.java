package com.spring.boot.manager.repository;

import com.myweb.pojo.Help;
import com.myweb.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Help.class, idClass = Integer.class)
public interface HelpRepository extends JpaRepository<Help, Integer> {

    public void removeAllByUser(User user);

    public Page<Help> findByUser(User user, Pageable pageable);

    public Page<Help> findByUserAndAudienceNot(User user, int audience, Pageable pageable);

    public List<Help> findByUser(User user);

    public Page<Help> findByDraftAndAudience(int draft, int audience, Pageable pageable);

    public List<Help> findByDraft(int draft);

    public Page<Help> findByUserAndDraftAndTagContains(User user, int draft, String tag, Pageable pageable);

    public Page<Help> findByUserAndAudienceAndDraftAndTagContains(User user, int audience, int draft, String tag, Pageable pageable);

    public Page<Help> findByDesignAndAudienceAndDraft(String design, int audience, int draft, Pageable pageable);

    public Page<Help> findByDraftAndAudienceAndTagContains(int draft, int audience, String tag, Pageable pageable);

    public Page<Help> findByUserAndDraftAndAudience(User user, int draft, int audience, Pageable pageable);

    public Page<Help> findByUserAndDraft(User user, int draft, Pageable pageable);

    @Query("select sum(help.studied) from Help help")
    public Long sumStudied();

    @Query("select help.user from Help help group by help.user order by count(help) desc")
    public Page<User> findUserByMost(Pageable pageable);

    @Query("select help  from Help help where help.user in (select follow.touser from Follow follow where follow.user = ?1 and help.draft = 4 and help.audience <> 3)")
    public Page<Help> queryByFollow(User user, Pageable pageable);

    @Query("select count(help) from Help help where help.user in (select follow.touser from Follow follow where follow.user = ?1) and help.createtime >= ?2")
    public int countByFollow(User user, String time);

    public Integer countAllByUser(User user);

    public Integer countAllByUserAndAudience(User user, Integer audience);

    public List<Help> findTop3ByUserAndAudienceOrderByStudiedDesc(User user, Integer audience);

    @Query("select help  from Help help where (help.audience = 1  or help.user = ?1  or (help.user in (select follow.touser from Follow follow where follow.user = ?1) and help.audience =2)) and help.draft =4")
    public Page<Help> queryAll(User user, Pageable pageable);

    @Query("select help  from Help help where (help.audience = 1 or help.user = ?1  or (help.user in (select follow.touser from Follow follow where follow.user = ?1) and help.audience =2)) and help.draft =4 and help.tag like ?2")
    public Page<Help> queryAllByTag(User user, String tag, Pageable pageable);

    @Query("select help  from Help help where (help.audience = 1 or help.user = ?1  or (help.user in (select follow.touser from Follow follow where follow.user = ?1) and help.audience =2)) and help.draft =4 and help.design = ?2")
    public Page<Help> queryAllByDesign(User user, String design, Pageable pageable);

    @Query("select help  from Help help where (help.audience = 1 or help.user = ?1  or (help.user in (select follow.touser from Follow follow where follow.user = ?1) and help.audience =2)) and help.draft =4 and help.design = ?2")
    public Page<Help> queryAllByDesignMine(User user, String design, Pageable pageable);

    @Query("select help  from Help help where (help.audience = 1 or (help.audience = 3 and help.user = ?2)) and help.user=?1")
    public Page<Help> queryAllByMine(User user, User user1, Pageable pageable);

    @Query("select help  from Help help where (help.audience = 1 or (help.audience = 3 and help.user = ?2))and help.user=?1 and help.tag like ?2")
    public Page<Help> queryAllByTagMine(User user, User user1, String tag, Pageable pageable);
}