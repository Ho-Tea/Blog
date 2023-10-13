package dev.be.blog.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    @NotBlank(message = "보드의 이름을 입력하지 않았습니다.")
    private String boardName;

}
