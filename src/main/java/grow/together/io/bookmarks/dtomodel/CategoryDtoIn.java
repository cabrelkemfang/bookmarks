package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.validation.UniqueCategoryName;
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
