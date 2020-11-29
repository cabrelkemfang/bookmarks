package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    @Query("select r from Role r where r.isDelete=false ")
    Page<Role> findAll(Pageable pageable);

    Optional<Role> findByName(String name);
}
