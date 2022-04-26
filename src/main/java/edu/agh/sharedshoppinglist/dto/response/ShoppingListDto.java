package edu.agh.sharedshoppinglist.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.agh.sharedshoppinglist.model.Session;
import edu.agh.sharedshoppinglist.model.ShoppingList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingListDto {
    String name;
    String code;
    List<ProductResponse> products;

    public static ShoppingListDto prepare(Session session, ShoppingList shoppingList) {
        List<ProductResponse> products = shoppingList.getProducts()
                .stream()
                .map(product -> ProductResponse.prepare(session, product))
                .collect(Collectors.toList());
        return new ShoppingListDto(shoppingList.getName(), shoppingList.getCode(), products);
    }
}
