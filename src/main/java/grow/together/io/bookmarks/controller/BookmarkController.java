package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoOut;
import grow.together.io.bookmarks.service.BookmarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks/v1/post")
public class BookmarkController {

    private final BookmarksService bookmarksService;

    @Autowired
    public BookmarkController(BookmarksService bookmarksService) {
        this.bookmarksService = bookmarksService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageableResult<BookmarkDtoOut> getAllBookmark(@RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.getAllBookmark(page, size);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{postId}")
    public DataResponse<BookmarkDtoOut> getBookmarkById(@PathVariable Long postId) {
        return this.bookmarksService.getBookmarkById(postId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/category")
    public PageableResult<BookmarkDtoOut> getBookmarkByCategory(@RequestParam String categName,
                                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                                @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.getBookmarkByCategory(page, size, categName);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/search")
    public PageableResult<BookmarkDtoOut> searchBookmark(@RequestParam String searchBy,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "9") int size) {
        return this.bookmarksService.searchBookmark(page, size, searchBy);
    }

}
