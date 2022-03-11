package janghankyu.univforum.web.board.dto;

import janghankyu.univforum.domain.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotPostDto {
    private Board board;
    private Long num;

    public HotPostDto(Board board, Long num) {
        this.board = board;
        this.num = num;
    }
}
