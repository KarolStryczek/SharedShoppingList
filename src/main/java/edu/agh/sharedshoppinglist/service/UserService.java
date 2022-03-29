package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.User;
import edu.agh.sharedshoppinglist.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    SessionService sessionService;

    public void registerUser(String login, String password, String email, String phone) throws ApplicationException {
        validateUserLogin(login);

        User user = User.builder()
                .login(login)
                .password(password)
                .email(email)
                .phone(phone)
                .build();

        userRepository.save(user);
    }

    private void validateUserLogin(String login) throws ApplicationException {
        if (userRepository.getByLogin(login) != null) {
            throw new ApplicationException(ErrorCode.USER_LOGIN_ALREADY_TAKEN);
        }
    }

    public String loginUser(String login, String password) throws ApplicationException {
        User user = userRepository.getByLoginAndPassword(login, password);
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_LOGIN_OR_PASSWORD);
        }
        Session currentSession = sessionService.getCurrentUserSession(user);
        if (currentSession != null) {
            return currentSession.getSessionId();
        }
        return sessionService.createNewUserSession(user).getSessionId();
    }
}
