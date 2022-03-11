package janghankyu.univforum.web.comment.form;

import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.web.comment.dto.CommentAddDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentAddForm {

    private Long parentId;
    private String content;

    public CommentAddDto createCommentAddDto(Long boardId, Student student){
        return janghankyu.univforum.web.comment.dto.CommentAddDto.builder()
                .boardId(boardId)
                .student(student)
                .parentId(parentId)
                .content(content)
                .build();
    }

    @Builder
    public CommentAddForm(Long parentId, String content) {
        this.parentId = parentId;
        this.content = content;
    }
}
