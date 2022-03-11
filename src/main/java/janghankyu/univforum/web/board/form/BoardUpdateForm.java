package janghankyu.univforum.web.board.form;

import janghankyu.univforum.web.board.dto.BoardUpdateDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BoardUpdateForm {
    @NotBlank
    private String title;
    private String writer;
    @NotBlank
    private String content;

    @Builder
    public BoardUpdateForm(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public BoardUpdateDto createBoardUpdateDto(Long boardId) {
        return BoardUpdateDto.builder()
                    .boardId(boardId)
                    .title(title)
                    .content(content)
                    .build();
    }
}
