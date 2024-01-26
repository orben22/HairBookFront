package com.example.hairbookfront.data.datastore

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
import com.example.hairbookfront.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
        private const val SHOP_ID = "ShopId"
        private const val MODE = "Mode"
        private const val BOOKINGID_FOR_EDITING = "BookingIdForEditing"
        val firstName = stringPreferencesKey(FIRST_NAME)
        val lastName = stringPreferencesKey(LAST_NAME)
        val age = floatPreferencesKey(AGE)
        val phoneNumber = stringPreferencesKey(PHONE_NUMBER)
        val yearsOfExperience = intPreferencesKey(YEARS_OF_EXPERIENCE)
        val role = stringPreferencesKey(ROLE)
        val userId = stringPreferencesKey(USER_ID)
        val accessToken = stringPreferencesKey(ACCESS_TOKEN)
        val email = stringPreferencesKey(EMAIL)
        val shopId = stringPreferencesKey(SHOP_ID)
        val mode = stringPreferencesKey(MODE)
        val bookingIdForEditing = stringPreferencesKey(BOOKINGID_FOR_EDITING)
    }

    suspend fun setBookingIdForEditing(bookingId:String){
        dataStore.edit { preferences ->
            preferences[DataStorePreferences.bookingIdForEditing] = bookingId
        }
    }

    fun getBookingIdForEditing(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[bookingIdForEditing] ?: ""
        }
    }

    fun getMode(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[mode] ?: ""
        }
    }

    suspend fun setMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferences.mode] = mode
        }
    }

    fun getShopId(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[shopId] ?: ""
        }
    }

    suspend fun setShopId(id: String) {
        dataStore.edit { preferences ->
            preferences[shopId] = id
        }
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
            preferences[yearsOfExperience] ?: 2
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
        }
    }

    suspend fun storeBarberDetails(barberDetails: BarberDTO) {
        dataStore.edit { preferences ->
            preferences[firstName] = barberDetails.firstName
            preferences[lastName] = barberDetails.lastName
            preferences[yearsOfExperience] = barberDetails.yearsOfExperience
            preferences[role] = Constants.BarberRole
        }
    }

    suspend fun storeCustomerDetails(customerDetails: CustomerDTO) {
        dataStore.edit { preferences ->
            preferences[firstName] = customerDetails.firstName
            preferences[lastName] = customerDetails.lastName
            preferences[age] = customerDetails.age
            preferences[phoneNumber] = customerDetails.phoneNumber
            preferences[role] = Constants.CustomerRole
        }
    }

}