package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.PostDtoIn;
import grow.together.io.bookmarks.dtomodel.PostDtoOut;

public interface PostService {
    DataResponse<PostDtoOut> getPostById(Long post_id);

    PageableResult<PostDtoOut> getAllPost(int page, int size);

    DataResponse<Void> createPostByUser(PostDtoIn postDtoIn, String name);

    DataResponse<Void> updatePostByUser(Long post_id, PostDtoIn postDtoIn, String name);

    DataResponse<Void> deletePostByUser(Long post_id, String name);

    PageableResult<PostDtoOut> getAllPostByUserId(int page, int size, String name);

    DataResponse<PostDtoOut> getPostByUserIdAndPostId(Long post_id, String name);

    DataResponse<Void> updateVueOrLike(Long id, String status);

    PageableResult<PostDtoOut> getAllPostByAdmin(int page, int size);

    PageableResult<PostDtoOut> getMostLikePost(int page, int size);

    PageableResult<PostDtoOut> getRecentPost(int page, int size);

    PageableResult<PostDtoOut> getPostByCategory(int page, int size, String category_name);
}
