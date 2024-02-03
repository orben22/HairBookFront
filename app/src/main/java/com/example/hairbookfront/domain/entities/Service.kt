package com.example.hairbookfront.domain.entities

/**
 * Represents a service provided by a barber shop.
 *
 * @property serviceName The name of the service.
 * @property price The price of the service.
 * @property duration The duration of the service.
 * @property barberShopId The unique identifier of the barber shop that provides the service.
 * @property serviceId The unique identifier of the service.
 */
data class Service(
    var serviceName: String,
    var price: Float,
    var duration: Float,
    val barberShopId: String?,
    val serviceId: String?,
)