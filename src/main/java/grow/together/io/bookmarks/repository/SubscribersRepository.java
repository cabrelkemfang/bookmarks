package grow.together.io.bookmarks.repository;

import grow.together.io.bookmarks.domain.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubscribersRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByEmail(String email);

//    @Query("select s from Subscriber s where s.status= 'SUBSCRIBE' ")
//    Page<Subscriber> getAllSubscribers(Pageable pageable);

    @Query("select count (s.id) from  Subscriber s where s.status='SUBSCRIBE'")
    long countBySubscriber();
}
