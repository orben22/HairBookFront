package com.example.hairbookfront.domain.entities

/**
 * Represents a barber.
 *
 * @property firstName The first name of the barber.
 * @property lastName The last name of the barber.
 * @property yearsOfExperience The years of experience of the barber.
 */
data class BarberDTO(
    val firstName: String,
    val lastName: String,
    val yearsOfExperience: Int
)

/**
 * Represents a customer.
 *
 * @property firstName The first name of the customer.
 * @property lastName The last name of the customer.
 * @property age The age of the customer.
 * @property phoneNumber The phone number of the customer.
 */
data class CustomerDTO(
    val firstName: String,
    val lastName: String,
    val age: Float,
    val phoneNumber: String
)

/**
 * Represents a user.
 *
 * @property userId The unique identifier of the user.
 * @property email The email of the user.
 * @property password The password of the user.
 * @property role The role of the user.
 * @property accessToken The access token of the user.
 */
data class User(
    val userId: String?,
    val email: String,
    val password: String,
    val role: String,
    val accessToken: String?,
)

/**
 * Represents a barber sign up request.
 *
 * @property email The email of the barber.
 * @property password The password of the barber.
 * @property role The role of the barber.
 * @property details The details of the barber.
 */
data class CustomerSignUpRequest(
    val email: String,
    val password: String,
    val role: String,
    val details: CustomerDTO
)

/**
 * Represents a barber sign up request.
 *
 * @property email The email of the barber.
 * @property password The password of the barber.
 * @property role The role of the barber.
 * @property details The details of the barber.
 */
data class BarberSignUpRequest(
    val email: String,
    val password: String,
    val role: String,
    val details: BarberDTO
)

/**
 * Represents a login request.
 * @property email The email of the user.
 * @property password The password of the user.
 */
data class LoginRequest(
    val email: String,
    val password: String
)