package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtoModel.*;

public interface ReportService {
    DataResponse<SummaryReport> getSummaryReport();

    DataResponse<UserSummaryReport> getSummaryPostReportByUser(Long user_id);

}
