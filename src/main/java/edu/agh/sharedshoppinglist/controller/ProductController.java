package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.dto.request.AddProductForm;
import edu.agh.sharedshoppinglist.dto.request.MarkProductForm;
import edu.agh.sharedshoppinglist.dto.request.RemoveProductForm;
import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.model.Product;
import edu.agh.sharedshoppinglist.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController extends AbstractExceptionHandler {

    ProductService productService;

    @PostMapping("/{listCode}/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProduct(@RequestHeader("session-id") String sessionId, @PathVariable String listCode, @RequestBody AddProductForm form) throws ApplicationException {
        Product product = Product.builder()
                .name(form.getName())
                .by(form.getBy())
                .shop(form.getShop())
                .number(form.getNumber())
                .cost(form.getCost())
                .build();

        productService.addProductToList(sessionId, listCode, product);
    }

    @PostMapping("/{listCode}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@RequestHeader("session-id") String sessionId, @PathVariable String listCode, @RequestBody RemoveProductForm form) throws ApplicationException {
        productService.removeProduct(sessionId, listCode, form.getProductIndex());
    }

    @PostMapping("/{listCode}/mark")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markProduct(@RequestHeader("session-id") String sessionId, @PathVariable String listCode, @RequestBody MarkProductForm form) throws ApplicationException {
        productService.markProduct(sessionId, listCode, form.getProductIndex(), form.getMarked());
    }
}
