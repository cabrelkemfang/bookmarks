package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.PostDtoOut;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{post_id}")
    public DataResponse<PostDtoOut> getPostById(@PathVariable Long post_id) {
        return this.postService.getPostById(post_id);
    }

    @PutMapping(path = "/{post_id}/value/{status}")
    public DataResponse<Void> updateVueOrLike(@PathVariable Long post_id, @PathVariable String status) {
        return this.postService.updateVueOrLike(post_id, status);
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/post/category/{cat_name}")
    public PageableResult<PostDtoOut> getPostByCategory(@PathVariable String cat_name,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "9") int size) {
        return this.postService.getPostByCategory(page, size, cat_name);
    }

}
