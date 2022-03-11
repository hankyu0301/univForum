package janghankyu.univforum.domain.like;

import janghankyu.univforum.domain.board.Board;
import janghankyu.univforum.domain.student.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postlike_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime likeTime;

    @Builder
    public PostLike(Student student, Board board, LocalDateTime likeTime) {
        this.student = student;
        this.board = board;
        this.likeTime = likeTime;
    }
}
