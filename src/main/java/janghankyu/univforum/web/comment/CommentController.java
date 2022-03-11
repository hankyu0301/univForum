package janghankyu.univforum.web.comment;

import janghankyu.univforum.domain.comments.Comment;
import janghankyu.univforum.domain.comments.CommentService;
import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.domain.student.StudentService;
import janghankyu.univforum.web.comment.dto.CommentAddDto;
import janghankyu.univforum.web.comment.dto.CommentDto;
import janghankyu.univforum.web.comment.form.CommentAddForm;
import janghankyu.univforum.web.comment.form.CommentDeleteForm;
import janghankyu.univforum.web.comment.form.CommentUpdateForm;
import janghankyu.univforum.web.login.LoginForm;
import janghankyu.univforum.web.login.annotation.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main/board/")
public class CommentController {

    private final CommentService commentService;
    private final StudentService studentService;

    @GetMapping("{boardId}/comment")
    public List<CommentDto> getCommentList(@PathVariable Long boardId) {
        log.info("getComment : {}", boardId);
        List<Comment> comments = commentService.findComments(boardId);
        List<CommentDto> result = getCommentDtos(comments);
        return result;
    }

    private List<CommentDto> getCommentDtos(List<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();
        Map<Long, CommentDto> map =  new HashMap<>();
        comments.stream().forEach(c-> {
                    CommentDto commentDto = new CommentDto(c.getId(),c.getWriter().getNickname(), getContent(c), c.getWriteTime(), c.getIsDeleted());
                    map.put(commentDto.getId(), commentDto);
                    if(c.getParent() != null) map.get(c.getParent().getId()).getChildren().add(commentDto);
                    else result.add(commentDto);
                });
        return result;
    }

    private String getContent(Comment c) {
        return c.getIsDeleted() ? "삭제된 댓글 입니다." : c.getContent();
    }

    @PostMapping("{boardId}/comment")
    public String addComment(@PathVariable Long boardId, @RequestBody CommentAddForm commentAddForm, @Login LoginForm loginForm){
        log.info("addComment : {}, {]", boardId, commentAddForm.getContent());
        Student student = studentService.findStudent(loginForm.getEmail()).get();
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(boardId, student);
        commentService.addComment(commentAddDto);
        return "addComplete";
    }

    @DeleteMapping("{boardId}/comment")
    public String deleteComment(@PathVariable Long boardId, @RequestBody CommentDeleteForm commentDeleteForm) {
        log.info("deleteComment : {}", commentDeleteForm.getCommentId());

        commentService.deleteComment(commentDeleteForm.getCommentId());
        return "deleteComplete";
    }

    @PatchMapping("{boardId}/comment")
    public String updateComment(@PathVariable Long boardId, @RequestBody CommentUpdateForm commentUpdateForm) {
        log.info("updateComment : {}, {}", commentUpdateForm.getCommentId(), commentUpdateForm.getContent());

        commentService.updateComment(commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        return "updateComplete";
    }
}
