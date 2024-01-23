package com.example.hairbookfront.data.remote.DataSourcesImpls

import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBooking
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceCustomer
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBooking
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceCustomer
import com.example.hairbookfront.domain.entities.BarberShop
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImplCustomer @Inject constructor(
    private val apiServiceCustomer: ApiServiceCustomer
) : HairBookDataSourceCustomer {
    override suspend fun getAllShops(accessToken: String): Response<List<BarberShop>> {
        return apiServiceCustomer.getAllShops("Bearer $accessToken")
    }
}