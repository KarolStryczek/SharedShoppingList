package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
}
