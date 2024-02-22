package org.example.board.domain.posts;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import jakarta.persistence.EntityManager;
import org.example.board.domain.answer.QAnswer;
import org.example.board.domain.posts.dto.PostsDetailResponseDto;
import org.example.board.domain.posts.dto.PostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class QueryRepositoryImpl implements QueryRepository {

    private final JPAQueryFactory queryFactory;

    public QueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //최신 순
    @Override
    public Page<Posts> findAllByLatest(Pageable pageable) {
        QPosts posts = QPosts.posts;
        //페이지 조회 쿼리
        List<Posts> content = queryFactory
                .selectFrom(posts)
                .orderBy(posts.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(queryFactory
                .select(posts.count())
                .from(posts)
                .fetchOne()).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    //조회수 순
    @Override
    public Page<PostsDetailResponseDto> findAllOrderByViewCountDesc(Pageable pageable) {
        return findPostsSorted(pageable, QPosts.posts.viewCount.desc());
    }
    //댓글 많은 순
    @Override
    public Page<PostsDetailResponseDto> findAllOrderByAnswerCountDesc (Pageable pageable){
        return findPostsSorted(pageable, QAnswer.answer.count().desc());
    }

    private PageImpl<PostsDetailResponseDto> findPostsSorted (Pageable pageable, OrderSpecifier < ?>... orderByConditions){
        QPosts posts = QPosts.posts;
        QAnswer answer = QAnswer.answer;
        List<Tuple> results = queryFactory
                .select(posts.id, posts.title, posts.content,
                        posts.author, posts.createdDate, posts.modifiedDate, posts.viewCount, answer.count())
                .from(posts)
                .leftJoin(answer).on(answer.posts.eq(posts))
                .groupBy(posts.id)
                .orderBy(orderByConditions)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // tuple 결과를 Dto 리스트로 변환
        List<PostsDetailResponseDto> contents = results.stream().map(tuple -> {

            Integer viewCount = Optional.ofNullable(tuple.get(posts.viewCount)).orElse(0);
            Integer answerCount = Optional.ofNullable(tuple.get((answer.count()))).map(Long::intValue).orElse(0);// 댓글 수))

            return new PostsDetailResponseDto(
                    tuple.get(posts.id),
                    tuple.get(posts.title),
                    tuple.get(posts.content),
                    tuple.get(posts.author),
                    tuple.get(posts.createdDate),
                    tuple.get(posts.modifiedDate),
                    viewCount,
                    answerCount);
        }).collect(Collectors.toList());

        Long total = Optional.ofNullable(queryFactory
                .select(posts.count())
                .from(posts)
                .fetchOne()).orElse(0L);
            return new PageImpl<>(contents, pageable, total);
        }

    }
