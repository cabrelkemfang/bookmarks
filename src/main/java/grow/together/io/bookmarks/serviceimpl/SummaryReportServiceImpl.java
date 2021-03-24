package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import grow.together.io.bookmarks.repository.SubscribersRepository;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SummaryReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final SubscribersRepository subscribersRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public SummaryReportServiceImpl(UserRepository userRepository, SubscribersRepository subscribersRepository, BookmarkRepository bookmarkRepository) {
        this.userRepository = userRepository;
        this.subscribersRepository = subscribersRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public DataResponse<SummaryReport> getSummaryReport() {
        SummaryReport summaryReport = new SummaryReport();
        summaryReport.setToSubscribers(this.subscribersRepository.countBySubscriber());
        summaryReport.setTotalBookmarks(this.bookmarkRepository.countByBookmarks());
        summaryReport.setTotalUsers(this.userRepository.countByUsers());

        return new DataResponse<>("Summary Report", HttpStatus.OK.value(), summaryReport);
    }

    @Override
    public DataResponse<UserSummaryReport> getSummaryBookmarksReportByUser(Long userId) {

        UserSummaryReport userSummaryReport = new UserSummaryReport();
        userSummaryReport.setTotalPrivateBookmarks(this.bookmarkRepository.countBookmarksByStatus(userId, GroupStatus.PRIVATE));
        userSummaryReport.setTotalPublicBookmarks(this.bookmarkRepository.countBookmarksByStatus(userId, GroupStatus.PUBLIC));

        return new DataResponse<>("Summary Report", HttpStatus.OK.value(), userSummaryReport);
    }

}
