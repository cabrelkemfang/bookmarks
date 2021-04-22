package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoOut;
import grow.together.io.bookmarks.service.BookmarksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookmarks")
@Slf4j
public class BookmarkController {

    private final BookmarksService bookmarksService;

    @Autowired
    public BookmarkController(BookmarksService bookmarksService) {
        this.bookmarksService = bookmarksService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public PageableResult<BookmarkDtoOut> fetchBookmarks(@RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "12") int size) {
        return this.bookmarksService.fetchPublicBookmarks(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE, path = "/{bookmarkId}")
    public DataResponse<BookmarkDtoOut> findBookmark(@PathVariable Long bookmarkId) {
        return this.bookmarksService.findBookmark(bookmarkId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE, path = "/category")
    public PageableResult<BookmarkDtoOut> findBookmarkByCategory(@RequestParam String name,
                                                                 @RequestParam(required = false, defaultValue = "1") int page,
                                                                 @RequestParam(required = false, defaultValue = "12") int size) {
        return this.bookmarksService.getBookmarkByCategory(page, size, name);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE, path = "/search")
    public PageableResult<BookmarkDtoOut> searchPublicBookmark(@RequestParam String searchBy,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "12") int size) {
        return this.bookmarksService.searchPublicBookmark(page, size, searchBy);
    }

}
