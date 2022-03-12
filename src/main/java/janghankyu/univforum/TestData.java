package janghankyu.univforum;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import janghankyu.univforum.domain.board.BoardService;
import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.domain.like.PostLikeService;
import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.domain.student.StudentService;
import janghankyu.univforum.web.board.dto.BoardPostDto;
import janghankyu.univforum.web.board.form.BoardAddForm;
import janghankyu.univforum.web.like.dto.PostLikeDto;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Profile("testData")
@RequiredArgsConstructor
@Transactional
public class TestData {
    private final StudentService studentService;
    private final BoardService boardService;
    private final PostLikeService postLikeService;

    @PostConstruct
    public void init() throws IOException {
        Student student = new Student("qwe@naver.com", "qwe", "qwe", "qwe", "qwe", "qwe");
        Student joinStudent = studentService.join(student);

        for (int i = 0; i < 100; i++) {
            BoardAddForm boardAddForm = new BoardAddForm("FrontEnd 게시글"+i, "테스트 글입니다.", CategoryType.FRONT, null, null);
            BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(student);
            boardService.post(boardPostDto);
        }

        for (int i = 0; i < 100; i++) {
            BoardAddForm boardAddForm = new BoardAddForm("BackEnd 게시글"+i, "테스트 글입니다.", CategoryType.BACK, null, null);
            BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(student);
            boardService.post(boardPostDto);
        }
        postLikeService.pushLikeButton(new PostLikeDto(joinStudent, 1L));
        //board.getComments().add(new Comment("qwe", "qwe", LocalDateTime.now()));
    }
}
