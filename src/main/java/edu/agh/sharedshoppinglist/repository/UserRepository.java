package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User getByLogin(String login);
    User getByLoginAndPassword(String login, String password);
}
