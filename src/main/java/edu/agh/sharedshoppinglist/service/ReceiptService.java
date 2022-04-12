package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.model.Product;
import edu.agh.sharedshoppinglist.model.Receipt;
import edu.agh.sharedshoppinglist.repository.ProductRepository;
import edu.agh.sharedshoppinglist.repository.ReceiptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptService {

    ProductRepository productRepository;
    ReceiptRepository receiptRepository;

    SessionService sessionService;

    public void createReceipt(String sessionId, String listCode) {
        Receipt receipt = Receipt.builder()
                .user(sessionService.getActiveSessionById(sessionId).getUser())
                .build();

        List<Product> products = productRepository.getAllByListCodeAndMarkedTrue(listCode);
        products.forEach(p -> p.setReceipt(receipt));
        receiptRepository.save(receipt);
    }
}
