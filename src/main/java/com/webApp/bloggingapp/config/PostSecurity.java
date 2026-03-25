package com.webApp.bloggingapp.config;

import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("postSecurity") // ✅ name must match @PreAuthorize expression
@RequiredArgsConstructor
public class PostSecurity {

    private final PostRepo postRepo;

    public boolean isPostOwner(Authentication authentication, Integer postId) {
        // Get logged in user
        User currentUser = (User) authentication.getPrincipal();

        // Get post from DB
        Post post = postRepo.findById(postId).orElse(null);

        // Check if post exists and user owns it
        if (post == null) return false;

        return post.getUser().getUserId() == currentUser.getUserId();
    }
}