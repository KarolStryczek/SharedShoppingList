package edu.agh.sharedshoppinglist.dto.response;

import edu.agh.sharedshoppinglist.model.Product;
import edu.agh.sharedshoppinglist.model.Session;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductResponse {
    String name;
    String by;
    String shop;
    Double number;
    Double cost;
    Integer marked;

    public static ProductResponse prepare(Session session, Product product) {
        String currentUser = session.getUser().getLogin();

        Integer markedFlag = product.getMarkedBy() == null
                ? 0
                : product.getMarkedBy().equals(currentUser) ? 1 : 2;

        return ProductResponse.builder()
                .name(product.getName())
                .by(product.getBy())
                .shop(product.getShop())
                .number(product.getNumber())
                .cost(product.getCost())
                .marked(markedFlag)
                .build();
    }
}
