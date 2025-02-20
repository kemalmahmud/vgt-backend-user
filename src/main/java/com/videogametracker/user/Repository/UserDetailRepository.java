package com.videogametracker.user.Repository;

import com.videogametracker.user.Model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {

    @Query(value = "select usd from UserDetail usd where usd.user.userId = :userId")
    Optional<UserDetail> findByUserId(@Param("userId") String userId);
}
