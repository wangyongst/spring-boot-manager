package com.spring.boot.manager.repository;

import com.spring.boot.manager.entity.Help;
import com.spring.boot.manager.entity.Report;
import com.spring.boot.manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryDefinition(domainClass = Report.class, idClass = Integer.class)
public interface ReportRepository extends JpaRepository<Report, Integer> {

    public void deleteAllByUser(User user);

    public void deleteAllByTouser(User user);

    public void deleteAllByHelp(Help help);

    public List<Report> findAllByHelpIsNotNullOrderByCreatetimeDesc();

    public List<Report> findAllByTouserIsNotNullOrderByCreatetimeDesc();
}