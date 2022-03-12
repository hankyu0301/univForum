package janghankyu.univforum.domain.board;

import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.web.board.dto.HotPostDto;
import janghankyu.univforum.web.board.dto.PageResultDto;
import janghankyu.univforum.web.board.search.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {
    List<HotPostDto> todayHotPost();
    PageResultDto<Board> search(SearchCondition searchCondition, Pageable pageable);
    PageResultDto<Board> findBoardByPaging(Pageable pageable);
    PageResultDto<Board> classifyByCategory(CategoryType categoryType, Pageable pageable);

}
