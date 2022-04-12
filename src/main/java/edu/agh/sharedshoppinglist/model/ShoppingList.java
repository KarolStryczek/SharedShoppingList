package edu.agh.sharedshoppinglist.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

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
    List<Product> products;
}
