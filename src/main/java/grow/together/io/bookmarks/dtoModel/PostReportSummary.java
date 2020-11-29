package grow.together.io.bookmarks.dtoModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReportSummary {
    private String title;
    private long totalLike;
    private long totalVue;
}
