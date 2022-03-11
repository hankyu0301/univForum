package janghankyu.univforum.domain.comments;

import janghankyu.univforum.domain.board.Board;
import janghankyu.univforum.domain.student.Student;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Student writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    private String content;
    private LocalDateTime writeTime;
    private Boolean isDeleted = false;

    @Builder
    public Comment(Student writer, Board board, Comment parent, String content, LocalDateTime writeTime, Boolean isDeleted) {
        this.writer = writer;
        this.board = board;
        this.parent = parent;
        this.content = content;
        this.writeTime = writeTime;
        this.isDeleted = isDeleted;
    }

}
