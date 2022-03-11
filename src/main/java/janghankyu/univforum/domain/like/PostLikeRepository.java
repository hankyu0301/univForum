package janghankyu.univforum.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository  extends JpaRepository<PostLike,Long>, PostLikeCustomRepository {
}
