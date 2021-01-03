package grow.together.io.bookmarks.repository;


import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.domain.Posts;
import grow.together.io.bookmarks.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {

    @Query("select p from Posts p where p.link=:link and p.isDeleted=false")
    Optional<Posts> findByLink(@Param("link") String link);

    @Query("Select COUNT(p.id) from Posts p Where  p.isDeleted = false and p.categories  in (select c from Category c where c.id = :id)")
    long categoryCount(@Param("id") Long id);

    @Query("select p from Posts p where p.user.id =:userId and p.isDeleted = false")
    Page<Posts> findPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select p from Posts p where p.isDeleted = false")
    Page<Posts> findPostsByAdmin(Pageable pageable);

    @Query("select p from Posts p where p.isDeleted = false order by p.like desc ")
    Page<Posts> findMostLikePosts(Pageable pageable);

    @Query("select p from Posts p where p.isDeleted = false order by p.createdAt desc ")
    Page<Posts> findRecentPosts(Pageable pageable);

    @Query("select p from Posts p where p.status='PUBLIC' and p.isDeleted = false order by p.link asc")
    Page<Posts> findPosts(Pageable pageable);

    @Query("select p from Posts p where p.id =:postId and p.user.id =:userId and p.isDeleted=false")
    Optional<Posts> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("select count(p.id) from Posts p where p.user.id=:userId and p.status =:status and p.isDeleted=false ")
    long countPostByStatus(@Param("userId") Long userId, @Param("status") GroupStatus status);

    @Query("select count(p.id) from  Posts p where p.isDeleted=false")
    long countByPost();

    @Query("select p from Posts p where p.categories in :category and p.isDeleted=false")
    Page<Posts> findByCategoriesIn(@Param("category") Category category, Pageable pageable);

}
