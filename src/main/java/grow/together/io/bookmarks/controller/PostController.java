package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.PostDtoOut;
import grow.together.io.bookmarks.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks/v1/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageableResult<PostDtoOut> getAllPost(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "9") int size) {
        return this.postService.getAllPost(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{postId}")
    public DataResponse<PostDtoOut> getPostById(@PathVariable Long postId) {
        return this.postService.getPostById(postId);
    }

    @PutMapping(path = "/{postId}/value/{status}")
    public DataResponse<Void> updateVueOrLike(@PathVariable Long postId, @PathVariable String status) {
        return this.postService.updateVueOrLike(postId, status);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/recent")
    public PageableResult<PostDtoOut> getRecentPost(@RequestParam(required = false, defaultValue = "1") int page,
                                                    @RequestParam(required = false, defaultValue = "5") int size) {
        return this.postService.getRecentPost(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/most-like")
    public PageableResult<PostDtoOut> getMostLikePost(@RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "5") int size) {
        return this.postService.getMostLikePost(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/post/category/{categName}")
    public PageableResult<PostDtoOut> getPostByCategory(@PathVariable String categName,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "9") int size) {
        return this.postService.getPostByCategory(page, size, categName);
    }

}
