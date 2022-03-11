package janghankyu.univforum.web.board.form;

import janghankyu.univforum.domain.board.Board;
import janghankyu.univforum.domain.file.Attachment;
import janghankyu.univforum.domain.file.AttachmentType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardForm {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private List<Attachment> imageFiles;
    private List<Attachment> generalFiles;

    @Builder
    public BoardForm(Long id, String title, String writer, String content, List<Attachment> imageFiles, List<Attachment> generalFiles) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.imageFiles = imageFiles;
        this.generalFiles = generalFiles;
    }

    public static BoardForm createBoardForm(Board Board) {
        Map<AttachmentType, List<Attachment>> result = Board.getAttachedFiles().stream()
                .collect(Collectors.groupingBy(Attachment::getAttachmentType));

        return BoardForm.builder()
                    .id(Board.getId())
                    .title(Board.getTitle())
                    .writer(Board.getWriter().getNickname())
                    .content(Board.getContent())
                    .imageFiles(result.get(AttachmentType.IMAGE))
                    .generalFiles(result.get(AttachmentType.GENERAL))
                    .build();
    }
}
