package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u.id) from User u where u.isDelete=false and u.role.name =:role")
    long countByRole(@Param("role") String role);

    @Query("select u from User u where u.email =:email and u.isDelete=false ")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from User u where u.github =:github and u.isDelete=false ")
    Optional<User> findByGithub(@Param("github") String github);

    @Override
    @Query("select u from User u where u.isDelete=false")
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.isDelete= false and u.id=:userId")
    Optional<User> findById(@Param("userId") Long userId);


    @Query("select count (u.id) from User u where u.isDelete=false  and u.active=true")
    long countByUsers();

    @Query("UPDATE User u SET u.failedAttempt = :failAttempts WHERE u.email = :email")
    @Modifying
    void updateFailedAttempts(@Param("failAttempts") int failAttempts, @Param("email") String email);

    @Query(value = "Select * from user u where u.failed_attempt = :failedAttempt and TIMESTAMPDIFF(MINUTE, u.lock_time, NOW()) >= :duration", nativeQuery = true)
    List<User> getLockUser(int failedAttempt, int duration);
}
