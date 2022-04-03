package edu.agh.shopping_split.dto.response

import edu.agh.shopping_split.list.products.dto.ProductItem


data class ProductsListResponse(
    val products: List<ProductItem>
)
