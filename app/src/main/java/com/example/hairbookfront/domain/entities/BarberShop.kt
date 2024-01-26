package com.example.hairbookfront.domain.entities

data class BarberShop(
    val barberShopId: String?,
    val barberShopName: String,
    val barberName: String,
    val phoneNumber: String,
    val workingDays: List<Float>,
    val sundayHours: List<String>?,
    val mondayHours: List<String>?,
    val tuesdayHours: List<String>?,
    val wednesdayHours: List<String>?,
    val thursdayHours: List<String>?,
    val fridayHours: List<String>?,
    val saturdayHours: List<String>?,
    val totalRating: Float,
    val location: String,
    val description: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            barberShopName,
            "${barberShopName.first()}",
            barberShopName.lowercase(),
            barberName,
            "${barberName.first()}",
            barberName.lowercase(),
            location,
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}