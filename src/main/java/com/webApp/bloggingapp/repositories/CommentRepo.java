package com.webApp.bloggingapp.repositories;

import com.webApp.bloggingapp.entities.Comment;
import com.webApp.bloggingapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
