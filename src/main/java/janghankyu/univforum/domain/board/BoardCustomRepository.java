package janghankyu.univforum.domain.board;

import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.web.board.dto.HotPostDto;
import janghankyu.univforum.web.board.search.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {
    List<HotPostDto> todayHotPost();
    Page<Board> search(SearchCondition searchCondition, Pageable pageable);
    Page<Board> findBoardByPaging(Pageable pageable);
    Page<Board> classifyByCategory(CategoryType categoryType, Pageable pageable);

}
