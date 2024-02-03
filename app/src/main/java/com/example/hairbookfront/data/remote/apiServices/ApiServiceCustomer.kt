package com.example.hairbookfront.data.remote.apiServices

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Interface for customer related API services.
 */
interface ApiServiceCustomer {

    /**
     * Gets all the barber shops.
     *
     * @param authToken The authorization token of the customer.
     * @return A response containing a list of barber shops.
     */
    @GET("/customer/get-all-shops")
    suspend fun getAllShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>

    /**
     * Gets a barber shop by its id.
     *
     * @param authToken The authorization token of the customer.
     * @param barberShopId The id of the barber shop.
     * @return A response containing the barber shop.
     */
    @GET("/shared/get-shop_by_id")
    suspend fun getShopById(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<BarberShop>

}