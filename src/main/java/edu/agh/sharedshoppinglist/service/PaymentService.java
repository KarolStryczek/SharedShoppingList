package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.*;
import edu.agh.sharedshoppinglist.repository.PaymentRepository;
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
public class PaymentService {

    PaymentRepository paymentRepository;

    ShoppingListService shoppingListService;
    SessionService sessionService;

    public void createPayment(String sessionId, String listCode, String targetUserLogin, Double amount) {
        ShoppingList shoppingList = shoppingListService.getProductList(sessionId, listCode);

        User targetUser = shoppingList.getListUsers().stream()
                .filter(user -> user.getUser().getLogin().equals(targetUserLogin))
                .findFirst().orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND))
                .getUser();

        Payment payment = Payment.builder()
                .sourceUser(sessionService.getActiveSessionById(sessionId).getUser())
                .targetUser(targetUser)
                .list(shoppingList)
                .amount(amount)
                .build();

        paymentRepository.save(payment);
        shoppingListService.updateListUsersBalances(shoppingList, payment);
    }

    public List<Payment> getAllListPayments(String sessionId, String listCode) {
        ShoppingList userList = shoppingListService.getProductList(sessionId, listCode);
        return userList != null ? userList.getPayments() : Collections.emptyList();
    }
}
