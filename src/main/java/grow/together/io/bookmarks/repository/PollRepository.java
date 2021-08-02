package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
