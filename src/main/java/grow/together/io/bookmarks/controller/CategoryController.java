package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.dtoModel.CategoryDtoIn;
import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.ErrorValidatorDetail;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.errorHandler.DeleteNotAllowExeption;
import grow.together.io.bookmarks.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookmarks/admin/category")
@Api(value = "Category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve All  Category")
    public PageableResult<Category> getAllCategory(@RequestParam(defaultValue = "1", required = false) int page,
                                                   @RequestParam(defaultValue = "10", required = false) int size) {
        return this.categoryService.findAll(page, size);
    }

    @GetMapping(path = "/{categ_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve Category By Id")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error ", response = ErrorValidatorDetail.class)
    })
    public DataResponse<Category> getCategoryById(@PathVariable Long categ_id) {
        return this.categoryService.findById(categ_id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Category")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error ", response = ErrorValidatorDetail.class)
    })
    public DataResponse<Void> updateCategory(@Valid @RequestBody CategoryDtoIn categoryDtoIn, @PathVariable Long categ_id) {
        return this.categoryService.update(categ_id, categoryDtoIn);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Category")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error ", response = ErrorValidatorDetail.class)
    })
    public DataResponse<Void> createCategory(@Valid @RequestBody CategoryDtoIn categoryDtoIn) {
        return this.categoryService.save(categoryDtoIn);
    }

    @DeleteMapping(path = "/{categ_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Category")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error ", response = ErrorValidatorDetail.class)
    })
    public DataResponse<Void> DeleteCategory(@PathVariable Long categ_id) throws DeleteNotAllowExeption {
        return this.categoryService.delete(categ_id);
    }
}
