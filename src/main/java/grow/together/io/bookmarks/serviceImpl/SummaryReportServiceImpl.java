package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.dtoModel.*;
import grow.together.io.bookmarks.repository.PostRepository;
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
    private final PostRepository postRepository;

    @Autowired
    public SummaryReportServiceImpl(UserRepository userRepository, SubscribersRepository subscribersRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.subscribersRepository = subscribersRepository;
        this.postRepository = postRepository;
    }

    @Override
    public DataResponse<SummaryReport> getSummaryReport() {
        SummaryReport summaryReport = new SummaryReport();
        summaryReport.setToSubscribers(this.subscribersRepository.countBySubscriber());
        summaryReport.setTotalPosts(this.postRepository.countByPost());
        summaryReport.setTotalUsers(this.userRepository.countByUsers());

        return new DataResponse<>("Summary Report", HttpStatus.OK.value(), summaryReport);
    }

    @Override
    public DataResponse<UserSummaryReport> getSummaryPostReportByUser(Long userId) {

        UserSummaryReport userSummaryReport = new UserSummaryReport();
        userSummaryReport.setTotalPrivatePosts(this.postRepository.countPostByStatus(userId, GroupStatus.PRIVATE));
        userSummaryReport.setTotalPublicPosts(this.postRepository.countPostByStatus(userId, GroupStatus.PUBLIC));

        return new DataResponse<>("Summary Report", HttpStatus.OK.value(), userSummaryReport);
    }

}
