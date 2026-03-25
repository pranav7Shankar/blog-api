package com.webApp.bloggingapp.repositories;

import com.webApp.bloggingapp.entities.Category;
import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
