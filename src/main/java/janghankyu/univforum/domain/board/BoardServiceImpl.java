package janghankyu.univforum.domain.board;

import janghankyu.univforum.domain.category.Category;
import janghankyu.univforum.domain.category.CategoryRepository;
import janghankyu.univforum.domain.category.CategoryType;
import janghankyu.univforum.domain.comments.CommentRepository;
import janghankyu.univforum.domain.file.Attachment;
import janghankyu.univforum.domain.file.AttachmentService;
import janghankyu.univforum.web.board.dto.BoardPostDto;
import janghankyu.univforum.web.board.dto.BoardUpdateDto;
import janghankyu.univforum.web.board.dto.HotPostDto;
import janghankyu.univforum.web.board.search.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AttachmentService attachmentService;
    private final CategoryRepository categoryRepository;

    public Board post(BoardPostDto boardPostDto) throws IOException {
        List<Attachment> attachments = attachmentService.saveAttachments(boardPostDto.getAttachmentFiles());
        for(Attachment attachment : attachments) {
            log.info(attachment.getOriginFilename());
        }
        Category category = categoryRepository.save(boardPostDto.getCategory());
        Board board = boardPostDto.createBoard();
        board.setCategory(category);

        attachments.stream().forEach(i -> board.setAttachment(i));
        return boardRepository.save(board);
    }

    public Boolean update(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getBoardId()).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        board.setTitle(boardUpdateDto.getTitle());
        board.setContent(boardUpdateDto.getContent());
        return true;
    }

    public Boolean delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        commentRepository.deleteByBoardId(boardId);
        categoryRepository.delete(board.getCategory());
        boardRepository.delete(board);
        return true;
    }

    public Boolean updateHit(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        board.setHit(board.getHit()+1);
        return true;
    }

    @Transactional(readOnly = true)
    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(SearchCondition searchCondition, Pageable pageable) {
        return boardRepository.search(searchCondition, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(CategoryType categoryType, Pageable pageable) {
        return boardRepository.classifyByCategory(categoryType, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(Pageable pageable) {
        return boardRepository.findBoardByPaging(pageable);
    }

    @Transactional(readOnly = true)
    public List<HotPostDto> findHotPosts() {
        return boardRepository.todayHotPost();
    }
}
