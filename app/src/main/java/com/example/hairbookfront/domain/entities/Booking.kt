package com.example.hairbookfront.domain.entities

class Booking(
    val bookingId: String?,
    val service: Service,
    val date: String,
    val userId: String,
    val barbershopId: String
)
