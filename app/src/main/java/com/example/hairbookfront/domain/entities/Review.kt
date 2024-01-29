package com.example.hairbookfront.domain.entities

data class Review(
    val reviewId: String?,
    var firstName: String,
    var lastName: String,
    var review: String,
    var rating: Float,
    val timestamp: String,
    val userId: String,
    val barberShopId: String,
)

