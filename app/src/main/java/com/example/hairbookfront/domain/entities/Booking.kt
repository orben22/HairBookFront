package com.example.hairbookfront.domain.entities


/**
 * Represents a booking made by a user.
 *
 * @property bookingId The unique identifier of the booking.
 * @property barberShopName The name of the barber shop where the booking is made.
 * @property barberName The name of the barber for the booking.
 * @property customerName The name of the customer who made the booking.
 * @property serviceId The unique identifier of the service booked.
 * @property date The date of the booking.
 * @property userId The unique identifier of the user who made the booking.
 * @property barberShopId The unique identifier of the barber shop where the booking is made.
 */
data class Booking(
    val bookingId: String?,
    val barberShopName: String,
    val barberName: String?,
    val customerName: String?,
    val serviceId: String,
    val date: String,
    val userId: String,
    val barberShopId: String
)
