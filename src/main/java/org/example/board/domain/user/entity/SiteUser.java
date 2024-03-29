package org.example.board.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.board.common.BaseTimeEntity;
import org.example.board.domain.email.entity.VerificationCode;
import org.example.board.domain.image.entity.Image;
import org.example.board.domain.postslike.entity.PostsLike;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class SiteUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    @Enumerated
    private Role role;

    @OneToOne(mappedBy = "siteUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @OneToMany(mappedBy = "siteUser",cascade = CascadeType.ALL)
    private List<PostsLike> postsLikesUser = new ArrayList<>();

    @OneToMany(mappedBy = "siteUser")
    private List<VerificationCode> verificationCodes = new ArrayList<>();
    @Builder
    public SiteUser(String username, String nickname, String password, String email, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // 닉네임 변경을 위한 setter 메소드
    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    // 비밀번호 변경을 위한 setter 메소드
    public void changePassword(String password) {
        this.password = password;
    }
}
