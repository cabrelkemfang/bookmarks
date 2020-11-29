package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.PostDtoIn;
import grow.together.io.bookmarks.dtoModel.PostDtoOut;

public interface PostService {
    DataResponse<PostDtoOut> getPostById(Long post_id);

    PageableResult<PostDtoOut> getAllPost(int page, int size);

    DataResponse<Void> createPostByUser(Long user_id, PostDtoIn postDtoIn);

    DataResponse<Void> updatePostByUser(Long user_id, Long post_id, PostDtoIn postDtoIn);

    DataResponse<Void> deletePostByUser(Long user_id, Long post_id);

    PageableResult<PostDtoOut> getAllPostByUserId(Long user_id, int page, int size);

    DataResponse<PostDtoOut> getPostByUserIdAndPostId(Long user_id, Long post_id);

    DataResponse<Void> updateVueOrLike(Long id, String status);

    PageableResult<PostDtoOut> getAllPostByAdmin(int page, int size);

    PageableResult<PostDtoOut> getMostLikePost(int page, int size);

    PageableResult<PostDtoOut> getRecentPost(int page, int size);
}
