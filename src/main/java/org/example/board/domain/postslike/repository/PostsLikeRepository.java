package org.example.board.domain.postslike.repository;

import org.example.board.domain.posts.entity.Posts;
import org.example.board.domain.postslike.entity.PostsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsLikeRepository extends JpaRepository<PostsLike, Long> {

    // 게시글 좋아요 여부 확인
    boolean existsByPostsIdAndSiteUserId(Long postId, Long userId);

    // 특정 게시글 좋아요 수 조회
    long countByPostsId(Long postId);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM PostsLike pl WHERE pl.posts.id = :postId AND pl.siteUser.id = :userId")
    void deleteByPostsIdAndSiteUserId(Long postId, Long userId);

    @Query("SELECT p1.posts FROM PostsLike p1 WHERE p1.siteUser.id = :userId")
    List<Posts> findPostsLikeBySiteUserId(@Param("userId")Long userId);

}
