package com.spring.boot.manager.repository;

import com.myweb.pojo.Help;
import com.myweb.pojo.Message;
import com.myweb.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Message.class, idClass = Integer.class)
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("select message.touser from Message message where message.user = ?1 or message.touser = ?1 group by message.touser")
    public List<User> findTouserByUserOrTouser(User user);

    @Query("select message.user from Message message where message.user = ?1 or message.touser = ?1 group by message.user")
    public List<User> findUserByUserOrTouser(User user);

    @Query("select message from Message message where ( message.user = ?1 and message.touser = ?2) or (message.user = ?2 and message.touser = ?1)")
    public Page<Message> QueryByUserAndTouser(User user, User touser, Pageable pageable);

    public Message findTop1ByUserOrTouserOrderByCreatetimeDesc(User user, User touser);

    public List<Message> findByUserOrTouser(User user, User touser);

    @Query("select message,count (message) from Message message group by message.adminuser having message.adminuser = ?1")
    public List<Object[]> findByAdminuser(Integer adminuser);

    public Page<Message> findByAdminuserAndMessage(Integer adminuser, String message, Pageable pageable);

    public void deleteAllByUserAndTouser(User user, User touser);

    public int countAllByUser(User user);

    public int countAllByTouser(User touser);

    public int countAllByUserAndCreatetimeGreaterThan(User user, String time);

    public int countAllByTouserAndCreatetimeGreaterThan(User touser, String time);

    public int countAllByUserAndTouser(User user, User touser);

    public void removeAllByUserOrTouser(User user, User touser);

    public void removeAllByHelp(Help help);

    public List<Message> findAllByUserNotOrderByCreatetimeDesc(User uer);
}