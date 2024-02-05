package com.example.hairbookfront.data.remote.apiServices

import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Gets the number of barber shops owned by the authenticated barber.
 *
 * @param authToken The authorization token of the barber.
 * @return A response containing the number of barber shops.
 */
interface ApiServiceBooking {

    /**
     * Gets the number of barber shops owned by the authenticated barber.
     *
     * @param authToken The authorization token of the barber.
     * @return A response containing the number of barber shops.
     */
    @POST("booking/book-haircut")
    suspend fun bookHaircut(
        @Header("Authorization") authToken: String,
        @Body booking: Booking
    ): Response<Booking>

    /**
     * Updates a booking.
     *
     * @param authToken The authorization token of the user.
     * @param bookingId The id of the booking.
     * @param booking The updated booking details.
     * @return A response containing the updated booking details.
     */
    @PUT("booking/update-booking")
    suspend fun updateBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
        @Body booking: Booking
    ): Response<Booking>

    /**
     * Deletes a booking.
     *
     * @param authToken The authorization token of the user.
     * @param bookingId The id of the booking.
     * @return A response containing the id of the deleted booking.
     */
    @DELETE("booking/delete-booking")
    suspend fun deleteBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<String>

    /**
     * Gets the bookings of the authenticated user.
     *
     * @param authToken The authorization token of the user.
     * @return A response containing a list of bookings.
     */
    @GET("booking/user-bookings")
    suspend fun getUserBookings(
        @Header("Authorization") authToken: String,
    ): Response<List<Booking>>

    /**
     * Gets the closest booking of the authenticated user.
     *
     * @param authToken The authorization token of the user.
     * @return A response containing the closest booking.
     */
    @GET("booking/closest-booking")
    suspend fun getClosestBooking(
        @Header("Authorization") authToken: String,
    ): Response<Booking>

    /**
     * Gets the bookings for a barber shop.
     *
     * @param authToken The authorization token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A response containing a list of bookings.
     */
    @GET("shared/get-service-by-id")
    suspend fun getServiceBookings(
        @Header("Authorization") authToken: String,
        @Query("serviceId") serviceId: String,
    ): Response<Service>

    /**
     * Gets all services by barber shop.
     *
     * @param authToken The authorization token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A response containing a list of services.
     */
    @GET("booking/get-all-services-by-barbershop")
    suspend fun getAllServicesByBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
    ): Response<List<Service>>

    /**
     * Gets the available booking by day.
     *
     * @param authToken The authorization token of the user.
     * @param barberShopId The id of the barber shop.
     * @param date The date of the booking.
     * @return A response containing a list of available bookings.
     */
    @GET("booking/get-available-booking-by-day")
    suspend fun getAvailableBookingByDay(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("date") date: String,
    ): Response<List<Boolean>>

    /**
     * Gets the booking by id.
     *
     * @param authToken The authorization token of the user.
     * @param bookingId The id of the booking.
     * @return A response containing the booking details.
     */
    @GET("booking/get-booking-by-id")
    suspend fun getBookingById(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<Booking>
}