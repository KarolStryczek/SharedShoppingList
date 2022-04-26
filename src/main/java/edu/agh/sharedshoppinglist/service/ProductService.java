package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.Product;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.ShoppingList;
import edu.agh.sharedshoppinglist.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ShoppingListService shoppingListService;
    SessionService sessionService;

    ProductRepository productRepository;

    public void addProductToList(String sessionId, String listCode, Product product) throws ApplicationException {
        ShoppingList shoppingList = shoppingListService.getProductList(sessionId, listCode);
        product.setList(shoppingList);
        productRepository.save(product);
    }

    public void removeProduct(String sessionId, String listCode, Integer productIndex) throws ApplicationException {
        productRepository.delete(getProduct(sessionId, listCode, productIndex));
    }

    public void markProduct(String sessionId, String listCode, Integer productIndex, Boolean mark) throws ApplicationException {
        Session session = sessionService.getActiveSessionById(sessionId);
        String userLogin = session.getUser().getLogin();

        Product product = getProduct(sessionId, listCode, productIndex);

        if (mark) {
            if (product.getMarkedBy() != null && !product.getMarkedBy().equals(userLogin)) {
                throw new ApplicationException(ErrorCode.PRODUCT_ALREADY_MARKED_BY_ANOTHER_USER);
            } else {
                product.setMarkedBy(userLogin);
            }
        } else {
            if (product.getMarkedBy() != null && !userLogin.equals(product.getMarkedBy())) {
                throw new ApplicationException(ErrorCode.CANNOT_UNMARK_OTHER_USER_PRODUCT);
            } else {
                product.setMarkedBy(null);
            }
        }

        productRepository.save(product);
    }

    private Product getProduct(String sessionId, String listCode, Integer productIndex) throws ApplicationException {
        ShoppingList shoppingList = shoppingListService.getProductList(sessionId, listCode);
        if (productIndex >= shoppingList.getProducts().size()) {
            throw new ApplicationException(ErrorCode.INVALID_PRODUCT_INDEX);
        }
        return shoppingList.getProducts().get(productIndex);
    }
}
