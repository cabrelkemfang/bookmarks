package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryReport {
    private long totalPosts;
    private long toSubscribers;
    private long totalUsers;
}
