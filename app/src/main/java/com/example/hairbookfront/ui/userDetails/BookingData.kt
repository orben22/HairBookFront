package com.example.hairbookfront.ui.userDetails

data class BookingData(
    val bookingId:String?,
    val barberShopName: String,
    val service: ServiceData,
    val date: String,
    val costumerName: String?,
    val barberName: String?

)

data class ServiceData(
    val serviceId:String?,
    val serviceName:String,
    val price:String,
    val duration:String
)

val bookingData=BookingData(
    bookingId = "1",
    barberShopName = "XYZ",
    service = ServiceData("1","masturbation","100 bat","depends on you"),
    date = "01-01-2024 12:30" ,
    costumerName="Or Ben Ami",
    barberName="Bar Refali"
)