package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.service.PostService;
import grow.together.io.bookmarks.service.ReportService;
import grow.together.io.bookmarks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks/v1/admin")
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


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public DataResponse<Void> createAdmin(@Valid @RequestBody UserDtaoIn userDtaoIn) {
        return this.userService.createAdminUser(userDtaoIn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/user")
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public PageableResult<UserDtaoOut> getAllUser(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        return this.userService.getAllUser(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/user/{userId}")
    @PreAuthorize("hasAuthority('VIEW_USER')")
    public DataResponse<UserDtaoOut> getUserById(@PathVariable long userId) {
        return this.userService.getUserById(userId);
    }

    @PutMapping(path = "/user/{email}/status/{status}")
    @PreAuthorize("hasAuthority('UPDATE_USER_STATUS')")
    public DataResponse<Void> updateStatus(@PathVariable String email, @PathVariable boolean status) {
        return this.userService.updateUserStatus(email, status);
    }

    @GetMapping(path = "/summary-reports")
    @PreAuthorize("hasAuthority('SUMMARY_REPORTS')")
    public DataResponse<SummaryReport> getSummaryReports() {
        return this.reportService.getSummaryReport();
    }

    @GetMapping(path = "/posts")
    @PreAuthorize("hasAuthority('ADMIN_VIEW_POST')")
    public PageableResult<PostDtoOut> getAllPostByAdmin(@RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return this.postService.getAllPostByAdmin(page, size);
    }
}
