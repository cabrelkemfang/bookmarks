package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoIn;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoOut;

import java.io.IOException;

public interface BookmarksService {
    DataResponse<BookmarkDtoOut> findBookmark(Long post_id);

    PageableResult<BookmarkDtoOut> fetchPublicBookmarks(int page, int size);

    PageableResult<BookmarkDtoOut> searchPublicBookmark(int page, int size, String title);

    PageableResult<BookmarkDtoOut> searchBookmarkByAdmin(int page, int size, String title);

    PageableResult<BookmarkDtoOut> searchUserBookmark(int page, int size, String title);

    DataResponse<Void> createBookmark(BookmarkDtoIn bookmarkDtoIn) throws IOException;

    DataResponse<Void> deleteUserBookmark(Long bookmarkId);

    PageableResult<BookmarkDtoOut> fetchUserBookmarks(int page, int size);

    DataResponse<BookmarkDtoOut> getBookmarkByUserIdAndBookmarkId(Long post_id);

    PageableResult<BookmarkDtoOut> fetchBookmarks(int page, int size);

    PageableResult<BookmarkDtoOut> getBookmarkByCategory(int page, int size, String category_name);
}
