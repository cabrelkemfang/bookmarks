package grow.together.io.bookmarks.dtoModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorValidatorDetail {
    private String title;
    private int status;
    private String message;
    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
