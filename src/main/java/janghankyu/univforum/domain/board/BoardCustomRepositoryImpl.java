package janghankyu.univforum.domain.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.web.board.dto.HotPostDto;
import janghankyu.univforum.web.board.dto.PageResultDto;
import janghankyu.univforum.web.board.search.SearchCondition;
import janghankyu.univforum.web.board.search.SearchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static janghankyu.univforum.domain.board.QBoard.board;
import static janghankyu.univforum.domain.like.QPostLike.postLike;

@Slf4j
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    public final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<HotPostDto> todayHotPost() {
        return queryFactory
                .select(Projections.constructor(HotPostDto.class, postLike.board, postLike.board.count()))
                .from(postLike)
                .groupBy(postLike.board)
                .having(postLike.board.writeTime.stringValue().contains(LocalDate.now().toString()))
                .orderBy(postLike.board.count().desc(), postLike.board.writeTime.asc())
                .limit(3)
                .fetch();
    }


    public PageResultDto<Board> search(SearchCondition searchCondition, Pageable pageable) {

        QueryResults<Board> results = queryFactory
                .selectFrom(board)
                .where(isSearchable(searchCondition.getSearchType(), searchCondition.getContent()))
                .orderBy(board.writeTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long num = results.getTotal();

        Page<Board> boardPage = new PageImpl<>(content, pageable, num);
        int totalPages = boardPage.getTotalPages();

        return new PageResultDto<Board>(content,totalPages,pageable);
    }


    public PageResultDto<Board> findBoardByPaging(Pageable pageable) {
        log.info("pageable : {}", pageable.getOffset());

        QueryResults<Board> results = queryFactory
                .selectFrom(board)
                .orderBy(board.writeTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long num = results.getTotal();

        Page<Board> boardPage = new PageImpl<>(content, pageable, num);
        int totalPages = boardPage.getTotalPages();
        return new PageResultDto<Board>(content,totalPages,pageable);
    }


    public PageResultDto<Board> classifyByCategory(CategoryType categoryType, Pageable pageable) {
        QueryResults<Board> results = queryFactory
                .selectFrom(board)
                .where(board.category.categoryType.eq(categoryType))
                .orderBy(board.writeTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long num = results.getTotal();

        Page<Board> boardPage = new PageImpl<>(content, pageable, num);
        int totalPages = boardPage.getTotalPages();
        return new PageResultDto<Board>(content,totalPages,pageable);
    }

    BooleanBuilder isSearchable(SearchType searchType, String content) {
        if(searchType == SearchType.TIT) {
            return titleCt(content);
        } else if(searchType == SearchType.STUD) {
            return userEq(content);
        } else {
            return titleCt(content).or(contentCt(content));
        }
    }

    BooleanBuilder userEq(String content) {
        return nullSafeBuilder( () -> board.writer.nickname.eq(content) );
    }
    BooleanBuilder titleCt(String content) {
        return nullSafeBuilder( () -> board.title.contains(content) );
    }
    BooleanBuilder contentCt(String content) {
        return nullSafeBuilder( () -> board.content.contains(content) );
    }

    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
