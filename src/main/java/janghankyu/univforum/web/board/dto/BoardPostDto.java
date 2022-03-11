package janghankyu.univforum.web.board.dto;

import janghankyu.univforum.domain.board.Board;
import janghankyu.univforum.domain.category.Category;
import janghankyu.univforum.domain.file.AttachmentType;
import janghankyu.univforum.domain.student.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardPostDto {
    private Student writer;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Category category;
    private Map<AttachmentType, List<MultipartFile>> attachmentFiles = new ConcurrentHashMap<>();

    @Builder
    public BoardPostDto(Student writer, String title, String content, Category category, Map<AttachmentType, List<MultipartFile>> attachmentFiles) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.category = category;
        this.attachmentFiles = attachmentFiles;
    }

    public Board createBoard() {
        return Board.builder()
                    .writer(writer)
                    .title(title)
                    .writeTime(LocalDateTime.now())
                    .content(content)
                    .attachedFiles(new ArrayList<>())
                    .isDeleted(false)
                    .hit(0)
                    .build();
    }
}
