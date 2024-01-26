package com.example.hairbookfront.domain.entities

data class Service(
    var serviceName: String,
    var price: Float,
    var duration: Float,
    val barberShopId: String?,
    val serviceId: String?,
)