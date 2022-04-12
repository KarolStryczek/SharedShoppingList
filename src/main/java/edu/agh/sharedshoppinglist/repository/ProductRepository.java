package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getAllByListCodeAndMarkedTrue(String listCode);
}
