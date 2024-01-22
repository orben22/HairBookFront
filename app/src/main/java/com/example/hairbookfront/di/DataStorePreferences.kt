package com.example.hairbookfront.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        const val DATA = "Data"
        private const val FIRST_NAME = "FirstName"
        private const val LAST_NAME = "LastName"
        private const val AGE = "Age"
        private const val PHONE_NUMBER = "PhoneNumber"
        private const val YEARS_OF_EXPERIENCE = "YearsOfExperience"
        private const val EMAIL = "Email"
        private const val ROLE = "Role"
        private const val USER_ID = "UserId"
        private const val ACCESS_TOKEN = "AccessToken"
        val firstName = stringPreferencesKey(FIRST_NAME)
        val lastName = stringPreferencesKey(LAST_NAME)
        val age = floatPreferencesKey(AGE)
        val phoneNumber = stringPreferencesKey(PHONE_NUMBER)
        val yearsOfExperience = intPreferencesKey(YEARS_OF_EXPERIENCE)
        val role = stringPreferencesKey(ROLE)
        val userId = stringPreferencesKey(USER_ID)
        val accessToken = stringPreferencesKey(ACCESS_TOKEN)
        val email = stringPreferencesKey(EMAIL)
    }


    fun getFirstName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[firstName] ?: ""
        }
    }

    fun getLastName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[lastName] ?: ""
        }
    }

    fun getAge(): Flow<Float> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[age] ?: 0f
        }
    }

    fun getPhoneNumber(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[phoneNumber] ?: ""
        }
    }

    fun getYearsOfExperience(): Flow<Int> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[yearsOfExperience] ?: 0
        }
    }

    fun getRole(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[role] ?: ""
        }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[userId] ?: ""
        }
    }

    fun getAccessToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[accessToken] ?: ""
        }
    }

    suspend fun clear() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[email] ?: ""
        }
    }

    suspend fun storeUserDetails(user: User) {
        dataStore.edit { preferences ->
            preferences[userId] = user.userId ?: ""
            preferences[accessToken] = user.accessToken ?: ""
            preferences[email] = user.email

            when (val userDetails = user.details) {
                is BarberDTO -> storeBarberDetails(preferences, userDetails)
                is CustomerDTO -> storeCustomerDetails(preferences, userDetails)
            }
        }
    }

    private fun storeBarberDetails(preferences: MutablePreferences, barberDetails: BarberDTO) {
        preferences[firstName] = barberDetails.firstName
        preferences[lastName] = barberDetails.lastName
        preferences[yearsOfExperience] = barberDetails.yearsOfExperience
    }

    private fun storeCustomerDetails(
        preferences: MutablePreferences,
        customerDetails: CustomerDTO
    ) {
        preferences[firstName] = customerDetails.firstName
        preferences[lastName] = customerDetails.lastName
        preferences[age] = customerDetails.age
        preferences[phoneNumber] = customerDetails.phoneNumber
    }

}