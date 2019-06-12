package com.spring.boot.manager.repository;

        import com.spring.boot.manager.entity.Apply;
        import com.spring.boot.manager.entity.Role;
        import com.spring.boot.manager.entity.Role2Priv;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.repository.RepositoryDefinition;
        import org.springframework.stereotype.Repository;

@Repository
@RepositoryDefinition(domainClass = Role2Priv.class, idClass = Integer.class)
public interface Role2PrivRepository extends JpaRepository<Role2Priv, Integer> {

    void deleteAllByRole(Role role);
}