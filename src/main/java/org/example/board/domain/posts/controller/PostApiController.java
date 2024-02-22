package org.example.board.domain.posts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.board.domain.posts.Posts;
import org.example.board.domain.posts.PostsService;
import org.example.board.domain.posts.dto.PostsDetailResponseDto;
import org.example.board.domain.posts.dto.PostsResponseDto;
import org.example.board.domain.posts.dto.PostsSaveRequestDto;
import org.example.board.domain.posts.dto.PostsUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApiController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public Long save(@Valid @RequestBody PostsSaveRequestDto requestDto) throws IOException {
        return postsService.save(requestDto);
    }

    //수정
    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto requestDto, Principal principal) {
        return postsService.update(id, requestDto, principal.getName());
    }
    //특정 글 조회
    @GetMapping("/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    //삭제
    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    // 내가 작성한 글
    @GetMapping("/user/posts")
    public ResponseEntity<List<Posts>> getUserPosts() {
        List<Posts> userPosts = postsService.findPostsByCurrentUser();
        return ResponseEntity.ok(userPosts);

    }

    // 최신순 게시글
    @GetMapping("/posts/desc")
    public ResponseEntity<Page<Posts>> getPostsSortedByDesc(Pageable pageable) {
        Page<Posts> postsSortedByDesc = postsService.getPostsSortedByDesc(pageable);
        return ResponseEntity.ok(postsSortedByDesc);
    }

    //댓글 순
    @GetMapping("/posts/answer/desc")
    public ResponseEntity<Page<PostsDetailResponseDto>> getAnswerDesc(Pageable pageable) {
        Page<PostsDetailResponseDto> postsSortedByAnswerDesc = postsService.getPostsSortedByAnswerDesc(pageable);
        return ResponseEntity.ok(postsSortedByAnswerDesc);
    }

    //조회수 순
    @GetMapping("/posts/viewCount/desc")
    public ResponseEntity<Page<PostsDetailResponseDto>> getViewCountDesc(Pageable pageable) {
        Page<PostsDetailResponseDto> postsSortedByViewCountDesc = postsService.getPostsSortedByViewCountDesc(pageable);
        return ResponseEntity.ok(postsSortedByViewCountDesc);
    }

    @GetMapping("/posts/{id}/likes/count")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long id) {
        long likesCount = postsService.getLikesCount(id);
        return ResponseEntity.ok(likesCount);
    }
}