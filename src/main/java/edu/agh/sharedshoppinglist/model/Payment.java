package edu.agh.sharedshoppinglist.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "source_user")
    User sourceUser;

    @ManyToOne
    @JoinColumn(name = "target_user")
    User targetUser;

    @ManyToOne
    @JoinColumn(name="list_code")
    ShoppingList list;

    Double amount;
}
