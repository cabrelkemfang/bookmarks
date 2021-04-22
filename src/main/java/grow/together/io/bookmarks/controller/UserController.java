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
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final BookmarksService bookmarksService;
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public UserController(BookmarksService bookmarksService, UserService userService, ReportService reportService) {
        this.bookmarksService = bookmarksService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/bookmarks")
    @PreAuthorize("hasAuthority('CREATE_POST')")
    public DataResponse<Void> createBookmark(@Valid @RequestBody BookmarkDtoIn bookmarkDtoIn) throws IOException {
        return this.bookmarksService.createBookmark(bookmarkDtoIn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/bookmarks")
    @PreAuthorize("hasAuthority('VIEW_USER_POST')")
    public PageableResult<BookmarkDtoOut> fetchUserBookmarks(@RequestParam(required = false, defaultValue = "1") int page,
                                                              @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.fetchUserBookmarks(page, size);
    }

    @GetMapping(path = "bookmarks/{bookmarkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_POST')")
    public DataResponse<BookmarkDtoOut> getBookmarkByUserIdAndBookmarkId(@PathVariable Long bookmarkId) {
        return this.bookmarksService.getBookmarkByUserIdAndBookmarkId(bookmarkId);
    }

    @DeleteMapping(path = "/bookmarks/{bookmarkId}")
    @PreAuthorize("hasAuthority('DELETE_POST')")
    public DataResponse<Void> deleteBookmark(@PathVariable Long bookmarkId) {
        return this.bookmarksService.deleteUserBookmark(bookmarkId);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public DataResponse<Void> updateUser(@Valid @RequestBody UserDtaoIn userDtaoIn, Principal principal) {
        return this.userService.updateUser(userDtaoIn, principal.getName());
    }

    @GetMapping(path = "/{userId}/reports")
    @PreAuthorize("hasAuthority('SUMMARY_POST_REPORTS')")
    public DataResponse<UserSummaryReport> getBookmarkSummaryReports(@PathVariable Long userId) {
        return this.reportService.getSummaryBookmarksReportByUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/bookmarks/search")
    @PreAuthorize("hasAuthority('SEARCH_POST')")
    public PageableResult<BookmarkDtoOut> searchBookmark(@RequestParam String searchBy,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.searchUserBookmark(page, size, searchBy);
    }
}