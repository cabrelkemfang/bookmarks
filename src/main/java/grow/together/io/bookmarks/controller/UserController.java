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
@RequestMapping("/api/bookmarks/v1/user")
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/post")
    public DataResponse<Void> createPost(@Valid @RequestBody PostDtoIn postDtoIn) {
        return this.postService.createPostByUser(postDtoIn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/post")
    public PageableResult<PostDtoOut> getPostByUserId(@RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "9") int size) {
        return this.postService.getAllPostByUserId(page, size);
    }

    @GetMapping(path = "post/{post_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<PostDtoOut> getPostByUserIdAndPostId(@PathVariable Long post_id) {
        return this.postService.getPostByUserIdAndPostId(post_id);
    }

    @PostMapping(path = "/post/{post_id}")
    public DataResponse<Void> deletePost(@PathVariable Long post_id) {
        return this.postService.deletePostByUser(post_id);
    }

    @PutMapping()
    public DataResponse<Void> updateUser(@Valid @RequestBody UserDtaoIn userDtaoIn) {
        return this.userService.updateUser(userDtaoIn);
    }


    @PutMapping(path = "/post/{post_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<Void> updatePost(@PathVariable Long post_id, @Valid @RequestBody PostDtoIn postDtoIn) {
        return this.postService.updatePostByUser(post_id, postDtoIn);
    }

    @GetMapping(path = "/{user_id}/reports")
    public DataResponse<UserSummaryReport> getPostSummaryReports(@PathVariable Long user_id) {
        return this.reportService.getSummaryPostReportByUser(user_id);
    }
}