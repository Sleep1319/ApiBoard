package com.ung.apiboard.dto.board;

import com.ung.apiboard.domain.board.Board;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {

    @NotNull
    private Long id;

    @NotNull
    private Long boardId;

    @NotNull
    private String title;

    @NotNull
    private String content;


}
