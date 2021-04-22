package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.dtomodel.CategoryDtoIn;
import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.errorhandler.DeleteNotAllowExeption;

public interface CategoryService {
    DataResponse<Void> save(CategoryDtoIn category);

    PageableResult<Category> findAll(int page, int size);

    DataResponse<Void> delete(Long categoryId) throws DeleteNotAllowExeption;

    DataResponse<Category> findById(Long categoryId);

    PageableResult<Category> searchCategory(int page, int size, String category);
}
