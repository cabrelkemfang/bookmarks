package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.common.GetLoginUser;
import grow.together.io.bookmarks.common.VariableName;
import grow.together.io.bookmarks.domain.*;
import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.mapper.MetaDataMapper;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import grow.together.io.bookmarks.service.MetaDataService;
import grow.together.io.bookmarks.service.BookmarksService;
import grow.together.io.bookmarks.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import net.anotheria.moskito.aop.annotation.Monitor;
import net.bytebuddy.implementation.bytecode.Throw;
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

    private final BookmarkRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final MetaDataService metaDataService;
    private final GetLoginUser getLoginUser;
    private final MetaDataMapper metaDataMapper;

    @Autowired
    public BookmarksServiceImpl(BookmarkRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository, MetaDataService metaDataService, GetLoginUser getLoginUser, MetaDataMapper metaDataMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.metaDataService = metaDataService;
        this.getLoginUser = getLoginUser;
        this.metaDataMapper = metaDataMapper;
    }


    @Override
    public DataResponse<BookmarkDtoOut> getBookmarkById(Long bookmarkId) {
        Bookmarks post = getById(bookmarkId);

        return new DataResponse<>("Bookmarks Load Successfully", HttpStatus.OK.value(), new BookmarkDtoOut(post));
    }

    @Override
    public PageableResult<BookmarkDtoOut> getAllBookmark(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> posts = this.postRepository.findBookmarks(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchBookmarkByAdmin(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> posts = this.postRepository.searchBookmarksByAdmin(PageRequest.of(page - 1, size), title);

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchBookmarkByUser(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> posts = this.postRepository.searchBookmarksByUser(PageRequest.of(page - 1, size), title, getUser(this.getLoginUser.userName()).getId());

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> searchBookmark(int page, int size, String title) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        Page<Bookmarks> posts = this.postRepository.searchBookmarks(PageRequest.of(page - 1, size), title);

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }


    @SneakyThrows
    @Override
    @Transactional
    public DataResponse<Void> createBookmarkByUser(BookmarkDtoIn bookmarkDtoIn) throws IOException {

        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setStatus(GroupStatus.valueOf(bookmarkDtoIn.getStatus()));

        MetaDataDto metaDataDto = this.metaDataService.getMetaData(bookmarkDtoIn.getLink());

        bookmarks.setMetaData(this.metaDataMapper.map(metaDataDto));

        Category categories = this.categoryRepository.findByName(bookmarkDtoIn.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category With Name " + bookmarkDtoIn.getCategory() + " Not Found"));

        bookmarks.setCategories(categories);
        bookmarks.setUser(getUser(this.getLoginUser.userName()));

        this.postRepository.save(bookmarks);

        return new DataResponse<>("Bookmarks Created Successfully ", HttpStatus.CREATED.value());
    }

    @Override
    @Transactional
    public DataResponse<Void> deleteBookmarkByUser(Long bookmarkId) {
        this.postRepository.deleteBookmarks(bookmarkId, getUser(this.getLoginUser.userName()).getId());

        return new DataResponse<>("Bookmarks Deleted Successfully ", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public PageableResult<BookmarkDtoOut> getAllBookmarkByUserId(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }
        User user = getUser(this.getLoginUser.userName());

        Page<Bookmarks> posts = this.postRepository.findBookmarksByUserId(user.getId(), PageRequest.of(page - 1, size));
        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public DataResponse<BookmarkDtoOut> getBookmarkByUserIdAndBookmarkId(Long bookmarkId) {
        User user = getUser(this.getLoginUser.userName());
        Bookmarks post = this.postRepository.findByBookmarksIdAndUserId(bookmarkId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No result Found For Bookmark Id :" + bookmarkId + " And User Id " + user.getId()));

        return new DataResponse<>("Bookmarks Load Successfully", HttpStatus.OK.value(), new BookmarkDtoOut(post));
    }

    @Override
    public PageableResult<BookmarkDtoOut> getAllBookmarkByAdmin(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> posts = this.postRepository.findAll(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public PageableResult<BookmarkDtoOut> getBookmarkByCategory(int page, int size, String category_name) {
        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Bookmarks> posts = this.postRepository.findByCategoriesIn(category_name, PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.getContent().stream().map(BookmarkDtoOut::new).collect(Collectors.toList()));
    }

    Bookmarks getById(Long bookmarkId) {
        return this.postRepository.findById(bookmarkId).orElseThrow(() -> new ResourceNotFoundException("Bookmarks Not Found With id " + bookmarkId));
    }


    User getUser(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found With User Email " + email));
    }
}
