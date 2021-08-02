package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
