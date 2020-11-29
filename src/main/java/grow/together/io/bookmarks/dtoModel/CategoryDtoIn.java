package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.validator.annotation.UniqueCategoryName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoIn {
    @NotEmpty(message = "Please provide category name")
    @UniqueCategoryName
    private String name;
}
