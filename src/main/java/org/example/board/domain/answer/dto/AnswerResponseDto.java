package org.example.board.domain.answer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.board.domain.answer.entity.Answer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AnswerResponseDto {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private List<AnswerResponseDto> children;
    private String profileImageUrl;

    public AnswerResponseDto(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.author = answer.getSiteUser().getUsername();
        this.createdDate = answer.getCreatedDate();
    }

    public AnswerResponseDto(Answer answer, String profileImageUrl) {
        this(answer);
        this.profileImageUrl = profileImageUrl;
    }
}
