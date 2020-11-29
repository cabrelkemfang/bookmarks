package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.dtoModel.CategoryDtoIn;
import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.errorHandler.DeleteNotAllowExeption;

public interface CategoryService {
    DataResponse<Void> save(CategoryDtoIn category);

    PageableResult<Category> findAll(int page, int size);

    DataResponse<Void> update(Long catg_id, CategoryDtoIn categoryDto);

    DataResponse<Void> delete(Long catg_id) throws DeleteNotAllowExeption;

    DataResponse<Category> findById(Long catg_id);

    DataResponse<Category> searchCategory(String category);
}
