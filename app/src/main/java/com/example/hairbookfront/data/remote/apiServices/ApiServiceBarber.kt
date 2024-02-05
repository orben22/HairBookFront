package com.example.hairbookfront.data.remote.apiServices

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
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
 * Interface for barber related API services.
 */
interface ApiServiceBarber {
    /**
     * Gets the barber shops owned by the authenticated barber.
     *
     * @param authToken The authorization token of the barber.
     * @return A response containing a list of barber shops.
     */
    @GET("barber/get-my-barbershops")
    suspend fun getMyBarberShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>

    /**
     * Creates a new barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShop The barber shop to be created.
     * @return A response containing the created barber shop.
     */
    @POST("barber/create-barbershop")
    suspend fun createBarberShop(
        @Header("Authorization") authToken: String,
        @Body barberShop: BarberShop
    ): Response<BarberShop>

    /**
     * Gets a barber shop by its id.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @return A response containing the barber shop.
     */
    @GET("barber/get-barbershop-by-id")
    suspend fun getBarberShopById(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<BarberShop>

    /**
     * Deletes a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @return A response indicating the result of the delete operation.
     */
    @DELETE("barber/delete-barbershop")
    suspend fun deleteBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<String>

    /**
     * Updates a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @param barberShop The barber shop with updated details.
     * @return A response containing the updated barber shop.
     */
    @PUT("barber/update-barbershop")
    suspend fun updateBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Body barberShop: BarberShop
    ): Response<BarberShop>

    /**
     * Gets the reviews for a barber.
     *
     * @param authToken The authorization token of the barber.
     * @param barberId The id of the barber.
     * @return A response containing a list of reviews.
     */
    @GET("barber/get-reviews")
    suspend fun getReviews(
        @Header("Authorization") authToken: String,
        @Query("barberId") barberId: String
    ): Response<List<Review>>

    /**
     * Gets the closest booking for a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @return A response containing the closest booking.
     */
    @GET("barber/get-closest-booking")
    suspend fun getClosestBooking(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<Booking>

    /**
     * Gets the bookings for a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @return A response containing a list of bookings.
     */
    @GET("barber/my-bookings")
    suspend fun getMyBookings(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Booking>>

    /**
     * Creates a new service for a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @param service The service to be created.
     * @return A response containing the created service.
     */
    @POST("barber/create-service")
    suspend fun createService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Body service: Service
    ): Response<Service>

    /**
     * Updates a service for a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @param serviceId The id of the service.
     * @param service The service with updated details.
     * @return A response containing the updated service.
     */
    @PUT("barber/update-service")
    suspend fun updateService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("serviceId") serviceId: String,
        @Body service: Service
    ): Response<Service>

    /**
     * Deletes a service from a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @param serviceId The id of the service.
     * @return A response indicating the result of the delete operation.
     */
    @DELETE("barber/delete-service")
    suspend fun deleteService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("serviceId") serviceId: String
    ): Response<String>

    /**
     * Gets the services offered by a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @return A response containing a list of services.
     */
    @GET("barber/get-services")
    suspend fun getServices(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Service>>

    /**
     * Deletes a booking from a barber shop.
     *
     * @param authToken The authorization token of the barber.
     * @param barberShopId The id of the barber shop.
     * @param bookingId The id of the booking.
     * @return A response indicating the result of the delete operation.
     */
    @DELETE("barber/delete-booking")
    suspend fun deleteBooking(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("bookingId") bookingId: String
    ): Response<String>

    /**
     * Gets the number of barber shops owned by the authenticated barber.
     *
     * @param authToken The authorization token of the barber.
     * @return A response containing the number of barber shops.
     */
    @GET("barber/get-number-of-shops")
    suspend fun getNumberOfShops(
        @Header("Authorization") authToken: String
    ): Response<Int>

    @GET("barber/get-all-bookings")
    suspend fun getBarberBookings(
        @Header("Authorization") authToken: String
    ): Response<List<Booking>>

}