package edu.agh.sharedshoppinglist.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "sessions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_login")
    User user;

    LocalDateTime expirationDate;
}
