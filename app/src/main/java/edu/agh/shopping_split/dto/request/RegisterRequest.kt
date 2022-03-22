package edu.agh.shopping_split.dto.request

data class RegisterRequest(
    val login: String,
    val password: String,
    val email: String,
    val phone: String
)