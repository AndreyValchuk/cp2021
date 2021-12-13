package com.siard.movielibrary.dal.repositories;

import com.siard.movielibrary.dal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String username);
    Boolean existsByUsernameOrEmail(String username, String email);
}
