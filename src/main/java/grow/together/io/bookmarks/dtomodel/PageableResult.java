package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableResult<T> implements Serializable {
    private int page;
    private int size;
    private long totalOfItems;
    private int totalPage;
    private List<T> data;
}
