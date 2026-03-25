package com.webApp.bloggingapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
