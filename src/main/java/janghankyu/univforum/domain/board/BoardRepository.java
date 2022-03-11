package janghankyu.univforum.domain.board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardCustomRepository {

    @EntityGraph(attributePaths = {"writer","attachedFiles"})
    Optional<Board> findById(Long boardId);
}
