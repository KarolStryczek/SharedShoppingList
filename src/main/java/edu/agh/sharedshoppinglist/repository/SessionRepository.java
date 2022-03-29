package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SessionRepository extends JpaRepository<Session, String> {
    Session getByUserAndExpirationDateAfter(User user, LocalDateTime time);
    Session getBySessionIdAndExpirationDateAfter(String sessionId, LocalDateTime time);
}
