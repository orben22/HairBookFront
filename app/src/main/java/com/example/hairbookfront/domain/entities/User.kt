package com.example.hairbookfront.domain.entities

open class UserDetails
data class BarberDTO(
    val firstName: String,
    val lastName: String,
    val yearsOfExperience: Int
) : UserDetails()

class CustomerDTO(
    val firstName: String,
    val lastName: String,
    val age: Float,
    val phoneNumber: String
) : UserDetails()


class User(
    val userId: String?,
    val email: String,
    val password: String,
    val role: String,
    val details: UserDetails,
    val accessToken: String?,
)

class LoginRequest(
    val email: String,
    val password: String
)