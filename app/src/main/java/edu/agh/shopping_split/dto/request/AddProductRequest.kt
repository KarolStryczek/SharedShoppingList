package edu.agh.shopping_split.dto.request

data class AddProductRequest(
    val name: String,
    val by: String,
    val shop: String,
    val number: Double?,
    val cost: Double?
)
