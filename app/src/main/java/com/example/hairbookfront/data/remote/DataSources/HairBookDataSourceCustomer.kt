package com.example.hairbookfront.data.remote.DataSources

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import retrofit2.Response

interface HairBookDataSourceCustomer {

    suspend fun getAllShops(accessToken: String): Response<List<BarberShop>>

    suspend fun getShopById(accessToken: String, barbershopId: String): Response<BarberShop>

    suspend fun getDetailsCustomer(accessToken: String): Response<CustomerDTO>
}