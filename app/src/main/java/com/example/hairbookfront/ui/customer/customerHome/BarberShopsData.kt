package com.example.hairbookfront.ui.customer.customerHome

data class BarberShopsData(
    val barbershopName: String,
    val description: String,
    val location: String,
    val barberName: String
)

val barbershops = listOf(
    BarberShopsData(
        barbershopName = "Orel Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Oranit",
        barberName = "Orel"
    ),
    BarberShopsData(
        barbershopName = "Nahman Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Ariel",
        barberName = "Nahman"
    ),
    BarberShopsData(
        barbershopName = "Moshe Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Ariel",
        barberName = "Moshe"
    )
)



