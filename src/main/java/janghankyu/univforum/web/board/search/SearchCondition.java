package janghankyu.univforum.web.board.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCondition {
    private String content;
    private SearchType searchType;

    public SearchCondition(String content, SearchType searchType) {
        this.content = content;
        this.searchType = searchType;
    }
}
