package com.example.hairbookfront.domain.entities

class Review(
    val reviewId: String?,
    val firstName: String,
    val lastName: String,
    val review: String,
    val rating: String,
    val timestamp: String,
    val userId: String,
    val barbershopId: String,
)