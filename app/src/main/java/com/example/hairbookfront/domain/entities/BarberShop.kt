package com.example.hairbookfront.domain.entities

/**
 * Represents a barber shop.
 *
 * @property barberShopId The unique identifier of the barber shop.
 * @property barberShopName The name of the barber shop.
 * @property barberName The name of the barber.
 * @property phoneNumber The contact number of the barber shop.
 * @property workingDays The list of working days of the barber shop.
 * @property sundayHours The working hours of the barber shop on Sunday.
 * @property mondayHours The working hours of the barber shop on Monday.
 * @property tuesdayHours The working hours of the barber shop on Tuesday.
 * @property wednesdayHours The working hours of the barber shop on Wednesday.
 * @property thursdayHours The working hours of the barber shop on Thursday.
 * @property fridayHours The working hours of the barber shop on Friday.
 * @property saturdayHours The working hours of the barber shop on Saturday.
 * @property totalRating The total rating of the barber shop.
 * @property location The location of the barber shop.
 * @property description The description of the barber shop.
 */
data class BarberShop(
    var barberShopId: String?,
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
    /**
     * Checks if the barber shop matches the search query.
     * @param query The search query.
     * @return True if the barber shop matches the search query, false otherwise.
     */
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