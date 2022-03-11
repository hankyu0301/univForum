package janghankyu.univforum.domain.comments;

import janghankyu.univforum.web.comment.dto.CommentAddDto;

import java.util.List;

public interface CommentService {

    Comment addComment(CommentAddDto commentAddDto);

    List<Comment> findComments(Long boardId);

    Boolean deleteComment(Long commentId);

    Boolean deleteAll(Long boardId);

    Boolean updateComment(Long commentId, String content);
}
