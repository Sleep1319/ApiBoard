package com.ung.apiboard.dto.comment;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.board.Comment;
import com.ung.apiboard.domain.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    @NotNull(message = "회원정보 조회 불가")
    private Long memberId;

    @NotNull(message = "게시글 정보 조회 불가")
    private Long boardId;

    @NotNull(message = "댓글을 써주세요")
    private String comment;

    public static Comment toEntity(Board board, Member member, CommentCreateRequest req){
        return new Comment(board, member, req.comment);
    }
}
