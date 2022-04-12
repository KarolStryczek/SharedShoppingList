package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
