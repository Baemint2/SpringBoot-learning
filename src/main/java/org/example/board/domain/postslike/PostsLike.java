package org.example.board.domain.postslike;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.board.domain.posts.Posts;
import org.example.board.domain.user.Role;
import org.example.board.domain.user.SiteUser;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PostsLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    public Posts posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public SiteUser siteUser;

    private LocalDateTime likedAt;

    @Builder
    public PostsLike(Posts posts, SiteUser siteUser, LocalDateTime likedAt) {
        this.posts = posts;
        this.siteUser = siteUser;
        this.likedAt = likedAt;
    }
}