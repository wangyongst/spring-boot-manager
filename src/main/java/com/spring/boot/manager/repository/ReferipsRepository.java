package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Help;
import com.spring.boot.manager.entity.Referips;
import com.spring.boot.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Referips.class, idClass = Integer.class)
public interface ReferipsRepository extends JpaRepository<Referips, Integer> {
    public Referips findTop1ByUserAndHelpAndIp(User user, Help help, String ip);

    public void deleteAllByUser(User user);

    public void deleteAllByHelp(Help help);
}