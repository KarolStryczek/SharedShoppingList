package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.config.AppConfig;
import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.User;
import edu.agh.sharedshoppinglist.repository.SessionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {

    AppConfig appConfig;

    SessionRepository sessionRepository;

    public Session getActiveSessionById(String sessionId) throws ApplicationException {
        Session session = sessionRepository.getBySessionIdAndExpirationDateAfter(sessionId, LocalDateTime.now());
        if (session == null) {
            throw new ApplicationException(ErrorCode.INVALID_SESSION);
        }
        prolongSession(session);
        return session;
    }

    public Session getCurrentUserSession(User user) {
        Session currentSession =  sessionRepository.getByUserAndExpirationDateAfter(user, LocalDateTime.now());
        if (currentSession != null) {
            prolongSession(currentSession);
        }
        return currentSession;
    }

    private void prolongSession(Session session) {
        session.setExpirationDate(LocalDateTime.now().plusSeconds(appConfig.getSessionTimeout()));
        sessionRepository.save(session);
    }

    public Session createNewUserSession(User user) {
        Session session = Session.builder()
                .sessionId(generateSessionId())
                .user(user)
                .expirationDate(LocalDateTime.now().plusSeconds(appConfig.getSessionTimeout()))
                .build();

        return sessionRepository.save(session);
    }

    private String generateSessionId() {
        return RandomString.make(appConfig.getSessionIdLength());
    }
}
