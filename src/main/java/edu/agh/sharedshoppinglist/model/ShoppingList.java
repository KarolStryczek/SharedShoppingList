package edu.agh.sharedshoppinglist.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "lists")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ShoppingList {

    @Id
    String code;

    String name;

    @OrderBy("id")
    @OneToMany(mappedBy="list")
    @Where(clause = "receipt_id is null")
    @ToString.Exclude
    List<Product> products;

    @OrderBy("id")
    @OneToMany(mappedBy="list")
    @ToString.Exclude
    List<Receipt> receipts;

    @OrderBy("id")
    @OneToMany(mappedBy="list")
    @ToString.Exclude
    List<Payment> payments;

    @ToString.Exclude
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    List<ListUser> listUsers;

}
