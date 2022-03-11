package janghankyu.univforum.web.like.dto;

import janghankyu.univforum.domain.student.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeDto {
    private Student student;
    private Long boardId;

    @Builder
    public PostLikeDto(Student student, Long boardId) {
        this.student = student;
        this.boardId = boardId;
    }
}
