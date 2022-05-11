package edu.agh.sharedshoppinglist.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "receipts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name="user_login")
    User user;

    Double price;

    @ManyToOne
    @JoinColumn(name="list_code")
    ShoppingList list;

    @OneToMany(mappedBy="receipt", cascade = CascadeType.ALL)
    List<Product> products;

}
