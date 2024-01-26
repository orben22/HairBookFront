package com.example.hairbookfront.domain.entities

data class Review(
    val reviewId: String?,
    val firstName: String,
    val lastName: String,
    val review: String,
    val rating: Float,
    val timestamp: String,
    val userId: String,
    val barberShopId: String,
)

