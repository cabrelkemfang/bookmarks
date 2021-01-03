package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.*;
import grow.together.io.bookmarks.service.PostService;
import grow.together.io.bookmarks.service.ReportService;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
    public DataResponse<Void> createPost(@Valid @RequestBody PostDtoIn postDtoIn, Principal principal) {
        return this.postService.createPostByUser(postDtoIn, principal.getName());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/post")
    public PageableResult<PostDtoOut> getPostByUserId(@RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "9") int size, Principal principal) {
        return this.postService.getAllPostByUserId(page, size, principal.getName());
    }

    @GetMapping(path = "post/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<PostDtoOut> getPostByUserIdAndPostId(@PathVariable Long postId, Principal principal) {
        return this.postService.getPostByUserIdAndPostId(postId, principal.getName());
    }

    @PostMapping(path = "/post/{postId}")
    public DataResponse<Void> deletePost(@PathVariable Long postId, Principal principal) {
        return this.postService.deletePostByUser(postId, principal.getName());
    }

    @PutMapping()
    public DataResponse<Void> updateUser(@Valid @RequestBody UserDtaoIn userDtaoIn, Principal principal) {
        return this.userService.updateUser(userDtaoIn, principal.getName());
    }


    @PutMapping(path = "/post/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<Void> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDtoIn postDtoIn, Principal principal) {
        return this.postService.updatePostByUser(postId, postDtoIn, principal.getName());
    }

    @GetMapping(path = "/{userId}/reports")
    public DataResponse<UserSummaryReport> getPostSummaryReports(@PathVariable Long userId) {
        return this.reportService.getSummaryPostReportByUser(userId);
    }
}