package janghankyu.univforum.domain.board;

import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.web.board.dto.BoardPostDto;
import janghankyu.univforum.web.board.dto.BoardUpdateDto;
import janghankyu.univforum.web.board.dto.HotPostDto;
import janghankyu.univforum.web.board.search.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Board post(BoardPostDto boardPostDto) throws IOException;

    Boolean update(BoardUpdateDto boardUpdateDto);

    Boolean delete(Long boardId);

    Boolean updateHit(Long boardId);

    Board findBoard(Long boardId);

    Page<Board> findBoards(SearchCondition searchCondition, Pageable pageable);

    Page<Board> findBoards(CategoryType categoryType, Pageable pageable);

    Page<Board> findBoards(Pageable pageable);

    List<HotPostDto> findHotPosts();
}
