package edu.agh.sharedshoppinglist.service;

import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import edu.agh.sharedshoppinglist.model.Product;
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

    ProductRepository productRepository;

    public void addProductToList(String sessionId, String listCode, Product product) throws ApplicationException {
        ShoppingList shoppingList = shoppingListService.getProductList(sessionId, listCode);
        product.setList(shoppingList);
        productRepository.save(product);
    }

    public void removeProduct(String sessionId, String listCode, Integer productIndex) throws ApplicationException {
        productRepository.delete(getProduct(sessionId, listCode, productIndex));
    }

    public void markProduct(String sessionId, String listCode, Integer productIndex, Boolean isMarked) throws ApplicationException {
        Product product = getProduct(sessionId, listCode, productIndex);
        product.setMarked(isMarked);
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
