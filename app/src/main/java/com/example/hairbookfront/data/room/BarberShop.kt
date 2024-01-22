package com.example.hairbookfront.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barbershops")
data class BarberShopRoom(
    @PrimaryKey(autoGenerate = false)
    val barbershopId: String = "",
    val barbershopName: String,
    val phoneNumber: String,
    val workingDays: List<String>,
    val sundayHours: List<String>?,
    val mondayHours: List<String>?,
    val tuesdayHours: List<String>?,
    val wednesdayHours: List<String>?,
    val thursdayHours: List<String>?,
    val fridayHours: List<String>?,
    val saturdayHours: List<String>?,
    val totalRating: Int,
    val location: String,
    val description: String,
) {

}
