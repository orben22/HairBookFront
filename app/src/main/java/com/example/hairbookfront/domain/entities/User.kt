package com.example.hairbookfront.domain.entities

data class BarberDTO(
    val firstName: String,
    val lastName: String,
    val yearsOfExperience: Int
)

data class CustomerDTO(
    val firstName: String,
    val lastName: String,
    val age: Float,
    val phoneNumber: String
)


data class User(
    val userId: String?,
    val email: String,
    val password: String,
    val role: String,
    val accessToken: String?,
)

data class CustomerSignUpRequest(
    val email: String,
    val password: String,
    val role: String,
    val details: CustomerDTO
)

data class BarberSignUpRequest(
    val email: String,
    val password: String,
    val role: String,
    val details: BarberDTO
)

data class LoginRequest(
    val email: String,
    val password: String
)