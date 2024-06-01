package com.ung.apiboard.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Comment("유저 저장 이름")
    private String fileName;

    @Comment("실 저장 이름")
    private String storedFileName;

    @Comment("경로")
    private String fullPath;


    public Images(Board board , String fileName, String storedFileName, String fullPath) {
        this.board = board;
        this.fileName = fileName;
        this.storedFileName = storedFileName;
        this.fullPath = fullPath;
    }

}
