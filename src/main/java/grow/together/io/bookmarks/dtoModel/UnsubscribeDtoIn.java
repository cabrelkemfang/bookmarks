package grow.together.io.bookmarks.dtoModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnsubscribeDtoIn {
    private String email;
    private String reason;
}
