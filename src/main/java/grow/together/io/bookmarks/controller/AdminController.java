package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.*;
import grow.together.io.bookmarks.service.PostService;
import grow.together.io.bookmarks.service.ReportService;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookmarks/admin")
public class AdminController {

    private final PostService postService;
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public AdminController(PostService postService, UserService userService, ReportService reportService) {
        this.postService = postService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/user")
    public PageableResult<UserDtaoOut> getAllUser(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        return this.userService.getAllUser(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/user/{user_id}")
    public DataResponse<UserDtaoOut> getUserById(@PathVariable long user_id) {
        return this.userService.getUserById(user_id);
    }

    @PutMapping(path = "/user/{user_id}/status/{status}")
    public DataResponse<Void> updateStatus(@PathVariable Long user_id, @PathVariable boolean status) {
        return this.userService.updateUserStatus(user_id, status);
    }

    @GetMapping(path = "/summary-reports")
    public DataResponse<SummaryReport> getSummaryReports() {
        return this.reportService.getSummaryReport();
    }

    @GetMapping(path = "/posts")
    public PageableResult<PostDtoOut> getAllPostByAdmin(@RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return this.postService.getAllPostByAdmin(page, size);
    }
}
