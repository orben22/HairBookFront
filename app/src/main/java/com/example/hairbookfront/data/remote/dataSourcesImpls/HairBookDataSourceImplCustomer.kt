package com.example.hairbookfront.data.remote.dataSourcesImpls

import com.example.hairbookfront.data.remote.apiServices.ApiServiceCustomer
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceCustomer
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the [HairBookDataSourceCustomer] interface.
 */
class HairBookDataSourceImplCustomer @Inject constructor(
    private val apiServiceCustomer: ApiServiceCustomer
) : HairBookDataSourceCustomer {
    override suspend fun getAllShops(accessToken: String): Response<List<BarberShop>> {
        return apiServiceCustomer.getAllShops("Bearer $accessToken")
    }

    override suspend fun getShopById(accessToken: String, barbershopId: String): Response<BarberShop> {
        return apiServiceCustomer.getShopById("Bearer $accessToken", barbershopId)
    }

}