package edu.agh.shopping_split.dto.response

data class PaymentResponse(
   val sourceUser: String,
   val targetUser: String,
   val amount: Double
)
