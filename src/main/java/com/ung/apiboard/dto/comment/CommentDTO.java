package com.ung.apiboard.dto.comment;

import com.ung.apiboard.domain.board.Comment;
import com.ung.apiboard.domain.member.Member;
import lombok.Getter;

@Getter
public class CommentDTO {
    private Long id;
    private Member member;
    private String comment;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.member = comment.getMember();
        this.comment = comment.getComment();
    }
}
