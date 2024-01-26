package com.example.hairbookfront.domain.entities

data class Booking(
    val bookingId: String?,
    val barberShopName: String,
    val barberName: String?,
    val customerName: String?,
    val service: Service,
    val date: String,
    val userId: String,
    val barberShopId: String
)
