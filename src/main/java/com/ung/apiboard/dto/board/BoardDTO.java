package com.ung.apiboard.dto.board;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.member.Member;
import lombok.Getter;

@Getter
public class BoardDTO {

    private Long id;
    private String title;
    private Member member;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.member = board.getMember();
    }
}
