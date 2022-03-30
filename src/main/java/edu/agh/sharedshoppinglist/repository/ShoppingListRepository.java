package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, String> {
    ShoppingList getByCode(String code);
}
