package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.isu.portfolio.model.db.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    
    boolean existsByUsername(String username);
}
