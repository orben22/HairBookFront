package com.example.hairbookfront.data.remote.apiServices

import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interface for authentication related API services.
 */
interface ApiServiceAuth {
    /**
     * Logs in a user.
     *
     * @param loginRequest The login request containing the user's credentials.
     * @return A response containing the user's details.
     */
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>

    /**
     * Signs out a user.
     *
     * @param authToken The authorization token of the user.
     * @return A response indicating the result of the sign out operation.
     */
    @POST("auth/sign-out")
    suspend fun signOut(
        @Header("Authorization") authToken: String
    ): Response<String>

    /**
     * Gets the details of a customer.
     *
     * @param authToken The authorization token of the user.
     * @return A response containing the customer's details.
     */
    @GET("auth/get-details")
    suspend fun getDetailsCustomer(
        @Header("Authorization") authToken: String
    ): Response<CustomerDTO>

    /**
     * Gets the details of a barber.
     *
     * @param authToken The authorization token of the user.
     * @return A response containing the barber's details.
     */
    @GET("auth/get-details")
    suspend fun getDetailsBarber(
        @Header("Authorization") authToken: String
    ): Response<BarberDTO>

    /**
     * Signs up a customer.
     *
     * @param customerSignUpRequest The sign up request containing the customer's details.
     * @return A response containing the user's details.
     */
    @POST("auth/sign-up")
    suspend fun signUpCustomer(
        @Body customerSignUpRequest: CustomerSignUpRequest
    ): Response<User>

    /**
     * Signs up a barber.
     *
     * @param barberSignUpRequest The sign up request containing the barber's details.
     * @return A response containing the user's details.
     */
    @POST("auth/sign-up")
    suspend fun signUpBarber(
        @Body barberSignUpRequest: BarberSignUpRequest
    ): Response<User>

}