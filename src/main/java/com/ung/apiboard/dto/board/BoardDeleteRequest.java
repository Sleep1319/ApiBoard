package com.ung.apiboard.dto.board;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDeleteRequest {
    @NotNull
    private Long memberId;

    @NotNull
    private Long boardId;
}
