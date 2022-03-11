package janghankyu.univforum.web.like;

import janghankyu.univforum.domain.like.PostLikeService;
import janghankyu.univforum.domain.student.Student;
import janghankyu.univforum.domain.student.StudentService;
import janghankyu.univforum.web.like.dto.PostLikeDto;
import janghankyu.univforum.web.like.dto.PostLikeResponseDto;
import janghankyu.univforum.web.login.LoginForm;
import janghankyu.univforum.web.login.annotation.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/board/")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final StudentService studentService;

    @GetMapping("{boardId}/postLike")
    public PostLikeResponseDto getPostLikeInfo(@Login LoginForm loginForm, @PathVariable Long boardId){
        Student student = studentService.findStudent(loginForm.getEmail()).get();
        PostLikeDto postLikeDto = new PostLikeDto(student, boardId);
        PostLikeResponseDto postLikeInfo = postLikeService.getPostLikeInfo(postLikeDto);
        return postLikeInfo;
    }

    @PostMapping("{boardId}/postLike")
    public Boolean pushLikeButton(@Login LoginForm loginForm, @PathVariable Long boardId) {
        Student student = studentService.findStudent(loginForm.getEmail()).get();
        PostLikeDto postLikeDto = new PostLikeDto(student, boardId);

        return postLikeService.pushLikeButton(postLikeDto);
    }
}
