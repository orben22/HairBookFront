package com.example.hairbookfront.domain.entities

/**
 * Represents a review given by a user.
 *
 * @property reviewId The unique identifier of the review.
 * @property firstName The first name of the user who gave the review.
 * @property lastName The last name of the user who gave the review.
 * @property review The review text.
 * @property rating The rating given by the user.
 * @property timestamp The time when the review was given.
 * @property userId The unique identifier of the user who gave the review.
 * @property barberShopId The unique identifier of the barber shop that the review is for.
 */
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

