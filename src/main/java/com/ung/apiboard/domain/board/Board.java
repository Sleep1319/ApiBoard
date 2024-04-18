package com.ung.apiboard.domain.board;

import com.ung.apiboard.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String title;

    @Column
    private String content;

    /**
     * @ElementCollection
     * 자식 테이블을 별도로 만들어 관리
     * 이 방식은 부모 엔티티에 대한 자식 엔티티의 관계를 관리하기 위한 것이며, 각 자식 엔티티는 부모 엔티티에 대한 참조
     * 엔티티를 참조하여 별도의 테이블을 만드는 것과 유사
     */
    @ElementCollection
    private List<String> filePaths = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments;


    public Board(Member member, String title, String content, List<String> filePaths) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.filePaths = filePaths;
    }
}
