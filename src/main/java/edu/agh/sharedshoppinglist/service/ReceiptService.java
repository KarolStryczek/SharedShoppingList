package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.model.Product;
import edu.agh.sharedshoppinglist.model.Receipt;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.ShoppingList;
import edu.agh.sharedshoppinglist.repository.ProductRepository;
import edu.agh.sharedshoppinglist.repository.ReceiptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptService {

    ProductRepository productRepository;
    ReceiptRepository receiptRepository;

    ShoppingListService shoppingListService;
    SessionService sessionService;

    public void createReceipt(String sessionId, String listCode, Double price) {
        ShoppingList shoppingList = shoppingListService.getProductList(sessionId, listCode);
        Receipt receipt = Receipt.builder()
                .user(sessionService.getActiveSessionById(sessionId).getUser())
                .list(shoppingList)
                .price(price)
                .build();

        List<Product> products = productRepository.getAllByListCodeAndMarkedBy(listCode, receipt.getUser().getLogin());
        products.forEach(p -> p.setReceipt(receipt));
        receiptRepository.save(receipt);
    }

    public List<Receipt> getReceipts(String sessionId, String listCode) {
        Session session = sessionService.getActiveSessionById(sessionId);
        ShoppingList userList = session.getUser().getLists()
                .stream()
                .filter(list -> list.getCode().equals(listCode)).findFirst().orElse(null);

        return userList != null ? userList.getReceipts() : Collections.emptyList();
    }
}
