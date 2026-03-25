package com.webApp.bloggingapp.repositories;

import com.webApp.bloggingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Query(value = "DELETE FROM comments WHERE post_id IN (SELECT post_id FROM posts WHERE user_id = :userId)", nativeQuery = true)
    void deleteCommentsByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "DELETE FROM posts WHERE user_id = :userId", nativeQuery = true)
    void deletePostsByUserId(@Param("userId") Integer userId);
}
