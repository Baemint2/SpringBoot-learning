package org.example.board.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerUpdateRequestDto {

    private String content;
    private Long answerId;

    @Builder
    public AnswerUpdateRequestDto(String content, Long answerId) {
        this.content=content;
        this.answerId=answerId;
    }

}
