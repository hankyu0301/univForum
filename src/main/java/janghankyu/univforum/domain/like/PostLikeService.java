package janghankyu.univforum.domain.like;

import janghankyu.univforum.web.like.dto.PostLikeDto;
import janghankyu.univforum.web.like.dto.PostLikeResponseDto;

public interface PostLikeService {
    Boolean pushLikeButton(PostLikeDto postLikeDto);
    PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto);
}
