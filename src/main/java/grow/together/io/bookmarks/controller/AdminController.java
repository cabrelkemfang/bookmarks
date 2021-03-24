package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.service.BookmarksService;
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

    private final BookmarksService bookmarksService;
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public AdminController(BookmarksService bookmarksService, UserService userService, ReportService reportService) {
        this.bookmarksService = bookmarksService;
        this.userService = userService;
        this.reportService = reportService;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public DataResponse<Void> createAdmin(@Valid @RequestBody UserDtaoIn userDtaoIn) {
        return this.userService.createAdminUser(userDtaoIn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/users")
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

    @GetMapping(path = "/post")
    @PreAuthorize("hasAuthority('ADMIN_VIEW_POST')")
    public PageableResult<BookmarkDtoOut> getAllPostByAdmin(@RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return this.bookmarksService.getAllBookmarkByAdmin(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/post/search")
    @PreAuthorize("hasAuthority('SEARCH_POST')")
    public PageableResult<BookmarkDtoOut> searchBookmark(@RequestParam String searchBy,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.searchBookmarkByAdmin(page, size, searchBy);
    }
}
