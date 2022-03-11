package janghankyu.univforum.domain.file;

import janghankyu.univforum.domain.board.Board;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;
    private String originFilename;
    private String storeFilename;

    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Attachment(Long id, String originFilename, String storePath, AttachmentType attachmentType) {
        this.id = id;
        this.originFilename = originFilename;
        this.storeFilename = storePath;
        this.attachmentType = attachmentType;
    }

}
