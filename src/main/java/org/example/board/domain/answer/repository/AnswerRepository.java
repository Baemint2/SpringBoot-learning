package org.example.board.domain.answer.repository;

import org.example.board.domain.answer.entity.Answer;
import org.example.board.domain.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    void deleteBySiteUserId(Long id);

    @Query("SELECT a.posts FROM Answer a WHERE a.siteUser.username = :username")
    List<Posts> findPostsContentByUser(@Param("username") String username);

    List<Answer> findByPostsId(Long id);
}
