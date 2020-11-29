package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Permission;
import grow.together.io.bookmarks.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(@Param("name") String name);
}
