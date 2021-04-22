package grow.together.io.bookmarks.repository;


import grow.together.io.bookmarks.domain.GroupStatus;
import grow.together.io.bookmarks.domain.Bookmarks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmarks, Long> {

    @Query("select p from Bookmarks p where p.metaData.url=:link")
    Optional<Bookmarks> findByLink(@Param("link") String link);

    @Query("Select COUNT(p.postId) from Bookmarks p Where p.categories  in (select c from Category c where c.id = :id)")
    long categoryCount(@Param("id") Long id);

    @Query("select p from Bookmarks p where p.status='PUBLIC'  order by p.createdAt ")
    Page<Bookmarks> findPublicBookmarks(Pageable pageable);

    @Query("select p from Bookmarks p where p.status='PUBLIC' and p.metaData.title like %:title% or p.categories.name like %:title%")
    Page<Bookmarks> searchBookmarks(Pageable pageable, @Param("title") String title);

    @Query("select p from Bookmarks p where  p.metaData.title like %:title% or p.categories.name like %:title%")
    Page<Bookmarks> searchBookmarksByAdmin(Pageable pageable, @Param("title") String title);

    @Query("select p from Bookmarks p where p.user.id=:userId and  p.metaData.title like %:title% or p.categories.name like %:title%")
    Page<Bookmarks> searchBookmarksByUser(Pageable pageable, @Param("title") String title, @Param("userId") long userId);

    @Query("select p from Bookmarks p where p.postId =:postId and p.user.id =:userId")
    Optional<Bookmarks> findByBookmarksIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("select p from Bookmarks p where p.user.id =:userId order by p.createdAt ")
    Page<Bookmarks> findBookmarksByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select count(p.postId) from Bookmarks p where p.user.id=:userId and p.status =:status")
    long countBookmarksByStatus(@Param("userId") Long userId, @Param("status") GroupStatus status);

    @Query("select count(p.postId) from  Bookmarks p")
    long countByBookmarks();

    @Query("select p from Bookmarks p where p.categories.name  =:category and p.status='PUBLIC' ")
    Page<Bookmarks> findByCategoriesIn(@Param("category") String category, Pageable pageable);

    @Modifying
    @Query("delete from Bookmarks p where p.postId  =:bookmarkId and p.user.id=:userId ")
    void deleteBookmarks(@Param("bookmarkId") long bookmarkId, @Param("userId") long userId);
}
