package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.common.GetLoginUser;
import grow.together.io.bookmarks.common.VariableName;
import grow.together.io.bookmarks.domain.*;
import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.mapper.BookmarkMapper;
import grow.together.io.bookmarks.mapper.MetaDataMapper;
import grow.together.io.bookmarks.mapper.UserMapper;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import grow.together.io.bookmarks.service.MetaDataService;
import grow.together.io.bookmarks.service.BookmarksService;
import grow.together.io.bookmarks.repository.UserRepository;
import io.sentry.Sentry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookmarksServiceImpl implements BookmarksService {

    private final BookmarkRepository bookmarkRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final MetaDataService metaDataService;
    private final GetLoginUser getLoginUser;

    @Autowired
    public BookmarksServiceImpl(BookmarkRepository bookmarkRepository,
                                CategoryRepository categoryRepository,
                                UserRepository userRepository,
                                MetaDataService metaDataService,
                                GetLoginUser getLoginUser) {
        this.bookmarkRepository = bookmarkRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.metaDataService = metaDataService;
        this.getLoginUser = getLoginUser;
    }


    @Override
    public DataResponse<BookmarkDtoOut> findBookmark(Long bookmarkId) {
        Bookmarks bookmark = getById(bookmarkId);

        return new DataResponse<>("Bookmarks Load Successfully", HttpStatus.OK.value(), BookmarkMapper.map(bookmark));
    }

    @Override
    public PageableResult<BookmarkDtoOut> fetchPublicBookmarks(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> bookmarks = this.bookmarkRepository.findPublicBookmarks(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchBookmarkByAdmin(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> bookmarks = this.bookmarkRepository.searchBookmarksByAdmin(PageRequest.of(page - 1, size), title);

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchUserBookmark(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> bookmarks = this.bookmarkRepository.searchBookmarksByUser(PageRequest.of(page - 1, size), title, getUser(this.getLoginUser.userName()).getId());

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchPublicBookmark(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> bookmarks = this.bookmarkRepository.searchBookmarks(PageRequest.of(page - 1, size), title);

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }


    @SneakyThrows
    @Override
    @Transactional
    public DataResponse<Void> createBookmark(BookmarkDtoIn bookmarkDtoIn) throws IOException {
        try {
            this.bookmarkRepository.save(map(bookmarkDtoIn));
            return new DataResponse<>("Bookmarks Created Successfully ", HttpStatus.CREATED.value());

        } catch (Exception e) {
            Sentry.captureException(e);
            throw  new ResourceNotFoundException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public DataResponse<Void> deleteUserBookmark(Long bookmarkId) {
        this.bookmarkRepository.deleteBookmarks(bookmarkId, getUser(this.getLoginUser.userName()).getId());

        return new DataResponse<>("Bookmarks Deleted Successfully ", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public PageableResult<BookmarkDtoOut> fetchUserBookmarks(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        User user = getUser(this.getLoginUser.userName());

        Page<Bookmarks> bookmarks = this.bookmarkRepository.findBookmarksByUserId(user.getId(), PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    @Override
    public DataResponse<BookmarkDtoOut> getBookmarkByUserIdAndBookmarkId(Long bookmarkId) {

        Bookmarks bookmark;
        try {
            User user = getUser(this.getLoginUser.userName());
            bookmark = this.bookmarkRepository.findByBookmarksIdAndUserId(bookmarkId, user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No result Found For Bookmark Id :" + bookmarkId + " And User Id " + user.getId()));

            return new DataResponse<>("Bookmarks Load Successfully", HttpStatus.OK.value(), BookmarkMapper.map(bookmark));

        } catch (Exception e) {
            Sentry.captureException(e);
            throw new ResourceNotFoundException("No result Found For Bookmark Id :");
        }

    }

    @Override
    public PageableResult<BookmarkDtoOut> fetchBookmarks(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> bookmarks = this.bookmarkRepository.findAll(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> getBookmarkByCategory(int page, int size, String category_name) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> bookmarks = this.bookmarkRepository.findByCategoriesIn(category_name, PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                bookmarks.getTotalElements(),
                bookmarks.getTotalPages(),
                bookmarks.getContent().stream().map(BookmarkMapper::map).collect(Collectors.toList()));
    }

    Bookmarks getById(Long bookmarkId) {
        return this.bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmarks Not Found With id " + bookmarkId));
    }


    User getUser(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With User Email " + email));
    }

    private Bookmarks map(BookmarkDtoIn bookmarkDtoIn) throws IOException {

        MetaDataDto metaDataDto = this.metaDataService.getMetaData(bookmarkDtoIn.getLink());
        Category categories = this.categoryRepository.findByName(bookmarkDtoIn.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category With Name " + bookmarkDtoIn.getCategory() + " Not Found"));

        return BookmarkMapper.map(bookmarkDtoIn, metaDataDto, categories, getUser(this.getLoginUser.userName()));
    }
}
