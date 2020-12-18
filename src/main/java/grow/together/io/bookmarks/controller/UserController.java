package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.*;
import grow.together.io.bookmarks.service.PostService;
import grow.together.io.bookmarks.service.ReportService;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookmarks/user")
public class UserController {
    private final PostService postService;
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public UserController(PostService postService, UserService userService, ReportService reportService) {
        this.postService = postService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{user_id}/post")
    public DataResponse<Void> createPost(@Valid @RequestBody PostDtoIn postDtoIn, @PathVariable Long user_id) {
        return this.postService.createPostByUser(user_id, postDtoIn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{user_id}/post")
    public PageableResult<PostDtoOut> getPostByUserId(@PathVariable Long user_id,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "9") int size) {
        return this.postService.getAllPostByUserId(user_id, page, size);
    }

    @GetMapping(path = "/{user_id}/post/{post_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<PostDtoOut> getPostByUserIdAndPostId(@PathVariable Long user_id, @PathVariable Long post_id) {
        return this.postService.getPostByUserIdAndPostId(user_id, post_id);
    }

    @PostMapping(path = "/{user_id}/post/{post_id}")
    public DataResponse<Void> deletePost(@PathVariable Long user_id, @PathVariable Long post_id) {
        return this.postService.deletePostByUser(user_id, post_id);
    }

    @PutMapping(path = "/{user_id}")
    public DataResponse<Void> updateUser(@PathVariable Long user_d, @Valid @RequestBody UserDtaoIn userDtaoIn) {
        return this.userService.updateUser(user_d, userDtaoIn);
    }


    @PutMapping(path = "/{user_id}/post/{post_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<Void> updatePost(@PathVariable Long user_id, @PathVariable Long post_id, @Valid @RequestBody PostDtoIn postDtoIn) {
        return this.postService.updatePostByUser(user_id, post_id, postDtoIn);
    }

    @GetMapping(path = "/{user_id}/reports")
    public DataResponse<UserSummaryReport> getPostSummaryReports(@PathVariable Long user_id) {
        return this.reportService.getSummaryPostReportByUser(user_id);
    }
}