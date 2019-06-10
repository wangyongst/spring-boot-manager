package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByOpenid(String openid);

    public List<User> findByUsername(String username);

    public List<User> findByUsernameAndLocktimeGreaterThan(String username, String locktime);

    public List<User> findByEmailAndLocktimeGreaterThan(String username, String locktime);

    public List<User> findByEmail(String email);

    public Page<User> findAllByIdNot(Integer id, Pageable pageable);

    public Page<User> findByRefer(Integer refer, Pageable pageable);

    public Page<User> findAllByRefertimeGreaterThanOrderByReferDesc(String outtime, Pageable pageable);

    public List<User> findByRefer(Integer refer);

    public List<User> findByUsernameAndPassword(String username, String password);

    public List<User> findByEmailAndPassword(String email, String password);

    public Page<User> findAllByUsernameContainsOrNicknameContainsOrSexContainsOrJobsContains(String username, String nickname, String sex, String jobs, Pageable pageable);

    public int countAllByReferAndCreatetimeGreaterThan(Integer refer, String time);

    @Query("select user.jobs,count (user) from User user group by user.jobs order by count(user.jobs) desc")
    public List<Object[]> findAllTimes();
}