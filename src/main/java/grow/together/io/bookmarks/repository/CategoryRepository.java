package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("select c from Category c where c.name like %:name%")
    Category searchByName(@Param("name") String name);
}
