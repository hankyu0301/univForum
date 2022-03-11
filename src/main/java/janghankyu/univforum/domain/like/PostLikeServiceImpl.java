package janghankyu.univforum.domain.like;

import janghankyu.univforum.domain.board.Board;
import janghankyu.univforum.domain.board.BoardRepository;
import janghankyu.univforum.web.like.dto.PostLikeDto;
import janghankyu.univforum.web.like.dto.PostLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{

    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Boolean pushLikeButton(PostLikeDto postLikeDto) {
        postLikeRepository.exist(postLikeDto.getStudent().getId(),postLikeDto.getBoardId())
                .ifPresentOrElse(
                        postLike -> postLikeRepository.deleteById(postLike.getId()),
                        ()-> {
                            Board board = getBoard(postLikeDto);
                            postLikeRepository.save(new PostLike(postLikeDto.getStudent(), board, LocalDateTime.now()));
                        });

        return true;
    }
    @Transactional(readOnly = true)
    public Board getBoard(PostLikeDto postLikeDto) {
        return boardRepository.findById(postLikeDto.getBoardId()).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto) {
       long postLikeNum = getPostLikeNum(postLikeDto);
       boolean check = checkPushedLike(postLikeDto);

       return new PostLikeResponseDto(postLikeNum, check);
    }

    @Transactional(readOnly = true)
    public boolean checkPushedLike(PostLikeDto postLikeDto) {
        return postLikeRepository.exist(postLikeDto.getStudent().getId(),postLikeDto.getBoardId())
                .isPresent();
    }

    @Transactional(readOnly = true)
    public long getPostLikeNum(PostLikeDto postLikeDto) {
        return postLikeRepository.findPostLikeNum(postLikeDto.getBoardId());
    }
}
