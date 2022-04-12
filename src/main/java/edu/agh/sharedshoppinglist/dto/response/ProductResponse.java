package edu.agh.sharedshoppinglist.dto.response;

import edu.agh.sharedshoppinglist.model.Product;
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
    boolean marked;

    public static ProductResponse prepare(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .by(product.getBy())
                .shop(product.getShop())
                .number(product.getNumber())
                .cost(product.getCost())
                .marked(product.isMarked())
                .build();
    }
}
