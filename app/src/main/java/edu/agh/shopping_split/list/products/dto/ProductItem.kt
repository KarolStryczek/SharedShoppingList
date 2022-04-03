package edu.agh.shopping_split.list.products.dto

data class ProductItem(
    val name: String,
    val by: String,
    val shop: String,
    val num: String,
    val cost: String,
    var marked: Boolean
)
