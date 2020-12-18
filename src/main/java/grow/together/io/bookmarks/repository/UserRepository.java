package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u.id) from User u where u.isDelete=false and u.role.name =:role")
    long countByRole(@Param("role") String role);

    @Query("select u from User u where u.gmail =:email and u.isDelete=false ")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from User u where u.github =:github and u.isDelete=false ")
    Optional<User> findByGithub(@Param("github") String github);

    @Override
    @Query("select u from User u where u.isDelete=false")
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.isDelete= false and u.id=:user_id")
    Optional<User> findById(@Param("user_id") Long user_id);

    @Query("select count (u.id) from User u where u.isDelete=false  and u.active=true")
    long countByUsers();

    Optional<User> findByGmail(String email);

}
