package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    List<Receipt> findAllByListCode(String listCode);
}
