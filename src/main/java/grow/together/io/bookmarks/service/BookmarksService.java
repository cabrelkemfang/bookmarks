package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoIn;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoOut;

import java.io.IOException;

public interface BookmarksService {
    DataResponse<BookmarkDtoOut> getBookmarkById(Long post_id);

    PageableResult<BookmarkDtoOut> getAllBookmark(int page, int size);

    PageableResult<BookmarkDtoOut> searchBookmark(int page, int size, String title);

    PageableResult<BookmarkDtoOut> searchBookmarkByAdmin(int page, int size, String title);

    PageableResult<BookmarkDtoOut> searchBookmarkByUser(int page, int size, String title);

    DataResponse<Void> createBookmarkByUser(BookmarkDtoIn bookmarkDtoIn) throws IOException;

    DataResponse<Void> deleteBookmarkByUser(Long post_id);

    PageableResult<BookmarkDtoOut> getAllBookmarkByUserId(int page, int size);

    DataResponse<BookmarkDtoOut> getBookmarkByUserIdAndBookmarkId(Long post_id);

    PageableResult<BookmarkDtoOut> getAllBookmarkByAdmin(int page, int size);

    PageableResult<BookmarkDtoOut> getBookmarkByCategory(int page, int size, String category_name);
}
