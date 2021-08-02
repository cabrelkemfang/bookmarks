package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
