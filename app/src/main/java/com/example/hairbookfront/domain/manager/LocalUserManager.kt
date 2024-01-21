package com.example.hairbookfront.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>

//    suspend fun saveToken(token: String)
//
//    fun readToken(): Flow<String>



}