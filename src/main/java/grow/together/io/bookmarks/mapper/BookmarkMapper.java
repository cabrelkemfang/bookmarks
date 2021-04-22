package grow.together.io.bookmarks.mapper;

import grow.together.io.bookmarks.domain.Bookmarks;
import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoIn;
import grow.together.io.bookmarks.dtomodel.BookmarkDtoOut;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;

import java.time.format.DateTimeFormatter;


public final class BookmarkMapper {

    public static Bookmarks map(final BookmarkDtoIn bookmarkDtoIn, MetaDataDto metaDataDto, Category categories, User user) {

        final Bookmarks bookmarks = new Bookmarks();
        bookmarks.setStatus(GroupStatus.valueOf(bookmarkDtoIn.getStatus()));
        bookmarks.setMetaData(MetaDataMapper.map(metaDataDto));
        bookmarks.setCategories(categories);
        bookmarks.setUser(user);

        return bookmarks;
    }

    public static BookmarkDtoOut map(final Bookmarks bookmarks) {

        final BookmarkDtoOut bookmarkDtoOut = new BookmarkDtoOut();
        bookmarkDtoOut.setId(bookmarks.getPostId());
        bookmarkDtoOut.setLink(bookmarks.getMetaData().getUrl());
        bookmarkDtoOut.setCategory(bookmarks.getCategories().getName());
        bookmarkDtoOut.setImageLink(bookmarks.getMetaData().getImageLink());
        bookmarkDtoOut.setTitle(bookmarks.getMetaData().getTitle());
        bookmarkDtoOut.setStatus(bookmarks.getStatus().name());
        bookmarkDtoOut.setCreatedAt(bookmarks.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        return bookmarkDtoOut;
    }

}
