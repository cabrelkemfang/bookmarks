package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.dtoModel.CategoryDtoIn;
import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.errorHandler.BadRequestException;
import grow.together.io.bookmarks.errorHandler.DeleteNotAllowExeption;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.repository.PostRepository;
import grow.together.io.bookmarks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
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
    public DataResponse<Void> update(Long categ_id, CategoryDtoIn categoryDtoIn) {

        // check if category with id categ_id exist
        Category category = this.categoryRepository.findById(categ_id).orElseThrow(
                () -> new ResourceNotFoundException("Category With id :" + categ_id + " Not Found"));

        category.setName(categoryDtoIn.getName());
        this.categoryRepository.save(category);

        return new DataResponse<>("Category Successfully Updated", HttpStatus.CREATED.value());
    }

    @Override
    public DataResponse<Void> delete(Long catg_id) throws DeleteNotAllowExeption {
        long count = this.postRepository.categoryCount(catg_id);
        if (count != 0) {
            throw new DeleteNotAllowExeption("The Role Can't be deleted because it is currently been use");
        } else {
            this.categoryRepository.deleteById(catg_id);
        }
        return new DataResponse<>("Category Successfully Deleted", HttpStatus.NO_CONTENT.value());
    }

    @Override
    public DataResponse<Category> findById(Long catg_id) {
        Category category = this.categoryRepository.findById(catg_id).orElseThrow(
                () -> new ResourceNotFoundException("Category With Id :" + catg_id + " Not Found"));

        return new DataResponse<>("Category Successfully Deleted", HttpStatus.OK.value(), category);
    }

    @Override
    public DataResponse<Category> searchCategory(String value) {
        Category category = this.categoryRepository.searchByName(value);

        return new DataResponse<>("Search Result", HttpStatus.OK.value(), category);
    }
}
