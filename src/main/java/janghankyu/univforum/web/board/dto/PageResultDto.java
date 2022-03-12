package janghankyu.univforum.web.board.dto;

import janghankyu.univforum.domain.board.Board;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<E> {

    private List<E> entities;

    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;

    private List<Integer> pageList;

    public PageResultDto(List<E> entities,int totalPage, Pageable pageable){
        this.entities = entities;
        this.totalPage = totalPage;
        makePageList(pageable);
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
