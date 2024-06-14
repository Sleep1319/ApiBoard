package com.ung.apiboard.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDeleteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long boardId;

    @NotNull
    private Long commentId;
}
