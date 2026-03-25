package com.webApp.bloggingapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(name = "post_title", length=100, nullable = false)
    private String title;
    @Column(length = 10000)
    private String content;
    @Column(length = 500)
    private String imageUrl;
    @Column(columnDefinition = "datetime")
    private Date dateAdded;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private  Category category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();


}
