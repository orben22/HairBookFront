package com.example.hairbookfront.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BarberShopDao {

    @Upsert
    suspend fun upsertBarberShop(barberShop: BarberShopRoom)

    @Delete
    suspend fun deleteBarberShop(barberShop: BarberShopRoom)

    @Query("SELECT * FROM barbershops ORDER BY barbershopName ASC")
    fun orderBarberShopByName(): Flow<List<BarberShopRoom>>

    @Query("SELECT * FROM barbershops ORDER BY totalRating DESC")
    fun orderBarberShopByRating(): Flow<List<BarberShopRoom>>

    @Query("SELECT * FROM barbershops ORDER BY location ASC")
    fun orderBarberShopByLocation(): Flow<List<BarberShopRoom>>

}