package grow.together.io.bookmarks.dtoModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {
    private String message;
    private int status;
    private T data;

    public DataResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
