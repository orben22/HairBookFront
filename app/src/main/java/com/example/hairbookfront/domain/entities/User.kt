package com.example.hairbookfront.domain.entities

open class UserDetails
data class BarberDTO(
    val firstName: String,
    val lastName: String,
    val yearsOfExperience: Int
) : UserDetails()

data class CustomerDTO(
    val firstName: String,
    val lastName: String,
    val age: Float,
    val phoneNumber: String
) : UserDetails()


data class User(
    val userId: String?,
    val email: String,
    val password: String,
    val role: String,
    val details: UserDetails,
    val accessToken: String?,
)

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val role: String,
    val details: UserDetails
)

data class LoginRequest(
    val email: String,
    val password: String
)