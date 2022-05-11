package edu.agh.sharedshoppinglist.repository;

import edu.agh.sharedshoppinglist.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByListCode(String listCode);
}
