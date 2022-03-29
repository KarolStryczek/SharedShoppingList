package edu.agh.sharedshoppinglist.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    String login;
    String password;
    String email;
    String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "list_users",
            joinColumns = @JoinColumn(name = "user_login"),
            inverseJoinColumns = @JoinColumn(name = "list_code"))
    List<ShoppingList> lists;
}
