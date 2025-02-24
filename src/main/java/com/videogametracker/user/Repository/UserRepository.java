package com.videogametracker.user.Repository;

import com.videogametracker.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "select us from User us where us.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
