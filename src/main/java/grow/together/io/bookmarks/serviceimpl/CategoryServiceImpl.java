package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.dtomodel.CategoryDtoIn;
import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.DeleteNotAllowExeption;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import grow.together.io.bookmarks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, BookmarkRepository bookmarkRepository) {
        this.categoryRepository = categoryRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public DataResponse<Void> save(CategoryDtoIn categoryDtoIn) {
        Category category = new Category();
        category.setName(categoryDtoIn.getName());
        this.categoryRepository.save(category);

        return new DataResponse<>("Category Successfully Created", HttpStatus.CREATED.value());
    }

    @Override
    public PageableResult<Category> findAll(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Category> categories = this.categoryRepository.findAll(PageRequest.of(page - 1, size));

        return new PageableResult<>(page, size, categories.getTotalElements(), categories.getTotalPages(), categories.getContent());
    }

    @Override
    public DataResponse<Void> delete(Long catgId) throws DeleteNotAllowExeption {
        long count = this.bookmarkRepository.categoryCount(catgId);
        if (count != 0) {
            throw new DeleteNotAllowExeption("The Role Can't be deleted because it is currently been use");
        } else {
            this.categoryRepository.deleteById(catgId);
        }
        return new DataResponse<>("Category Successfully Deleted", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public DataResponse<Category> findById(Long catgId) {
        Category category = this.categoryRepository.findById(catgId).orElseThrow(
                () -> new ResourceNotFoundException("Category With Id :" + catgId + " Not Found"));

        return new DataResponse<>("Category Load Successfully", HttpStatus.OK.value(), category);
    }

    @Override
    public PageableResult<Category> searchCategory(int page, int size, String value) {

        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        Page<Category> categories = this.categoryRepository.searchByName(value, PageRequest.of(page - 1, size));

        return new PageableResult<>(page, size, categories.getTotalElements(), categories.getTotalPages(), categories.getContent());
    }
}
