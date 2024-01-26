package com.example.hairbookfront.domain.entities

data class Service(
    val serviceName: String,
    val price: Float,
    val duration: Float,
    val barberShopId: String?,
    val serviceId: String?,
)