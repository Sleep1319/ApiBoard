package com.ung.apiboard.dto.board;

import com.ung.apiboard.domain.board.Board;
import com.ung.apiboard.domain.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<MultipartFile> files;

    public static Board toEntity(Member member, BoardCreateRequest req, List<String> fileUrl) {
        return new Board(member, req.title, req.content, fileUrl);
    }
}
