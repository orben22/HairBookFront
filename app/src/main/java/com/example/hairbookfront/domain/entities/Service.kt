package com.example.hairbookfront.domain.entities

data class Service(
    val serviceId: String?,
    val serviceName: String,
    val price: Double,
    val duration: Int,
)