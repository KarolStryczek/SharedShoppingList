package edu.agh.sharedshoppinglist.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name="list_code")
    @ToString.Exclude
    ShoppingList list;

    String name;
    String by;
    String shop;
    Double number;
    Double cost;
    String markedBy;

    @ManyToOne
    @JoinColumn(name="receipt_id")
    @ToString.Exclude
    Receipt receipt;

}
