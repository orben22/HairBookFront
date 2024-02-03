package com.example.hairbookfront.data.datastore

import androidx.datastore.core.DataStore
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
import javax.inject.Inject

/**
 * This class is responsible for managing the user's preferences using DataStore.
 * It provides methods to set and get various user details like first name, last name, age, etc.
 * It also provides methods to manage the application state like mode, role, etc.
 * @property dataStore The DataStore instance used to store and retrieve preferences.
 */
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
        private const val BOOKING_ID_FOR_EDITING = "BookingIdForEditing"
        private const val REVIEW_ID_FOR_EDITING = "ReviewIdForEditing"
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
        val bookingIdForEditing = stringPreferencesKey(BOOKING_ID_FOR_EDITING)
        val reviewIdForEditing = stringPreferencesKey(REVIEW_ID_FOR_EDITING)
    }

    /**
     * Sets the booking ID for editing.
     * @param bookingId The booking ID to be set for editing.
     */
    suspend fun setBookingIdForEditing(bookingId: String) {
        dataStore.edit { preferences ->
            preferences[bookingIdForEditing] = bookingId
        }
    }

    /**
     * Gets the booking ID for editing.
     * @return A flow of the booking ID for editing.
     */
    fun getBookingIdForEditing(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[bookingIdForEditing] ?: ""
        }
    }

    /**
     * Sets the review ID for editing.
     * @param reviewId The review ID to be set for editing.
     */
    suspend fun setReviewIdForEditing(reviewId: String) {
        dataStore.edit { preferences ->
            preferences[reviewIdForEditing] = reviewId
        }
    }

    /**
     * Gets the review ID for editing.
     * @return A flow of the review ID for editing.
     */
    fun getReviewIdForEditing(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[reviewIdForEditing] ?: ""
        }
    }


    /**
     * Gets the mode of the screen edit/create.
     * @return A flow of the mode of the application.
     */
    fun getMode(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[mode] ?: ""
        }
    }

    /**
     * Sets the mode of the screen edit/create.
     * @param mode The mode to be set.
     */
    suspend fun setMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[DataStorePreferences.mode] = mode
        }
    }

    /**
     * Gets the shop ID.
     * @return A flow of the shop ID.
     */
    fun getShopId(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[shopId] ?: ""
        }
    }

    /**
     * Sets the shop ID.
     * @param id The shop ID to be set.
     */
    suspend fun setShopId(id: String) {
        dataStore.edit { preferences ->
            preferences[shopId] = id
        }
    }


    /**
     * Gets the first name of the user.
     * @return A flow of the first name of the user.
     */
    fun getFirstName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[firstName] ?: ""
        }
    }

    /**
     * Gets the last name of the user.
     * @return A flow of the last name of the user.
     */
    fun getLastName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[lastName] ?: ""
        }
    }

    /**
     * Gets the age of the user.
     * @return A flow of the age of the user.
     */
    fun getAge(): Flow<Float> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[age] ?: 0f
        }
    }

    /**
     * Gets the phone number of the user.
     * @return A flow of the phone number of the user.
     */
    fun getPhoneNumber(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[phoneNumber] ?: ""
        }
    }

    /**
     * Gets the years of experience of the user.
     * @return A flow of the years of experience of the user.
     */
    fun getYearsOfExperience(): Flow<Int> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[yearsOfExperience] ?: 2
        }
    }

    /**
     * Gets the role of the user.
     * @return A flow of the role of the user.
     */
    fun getRole(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[role] ?: ""
        }
    }

    /**
     * Gets the user ID.
     * @return A flow of the user ID.
     */
    fun getUserId(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[userId] ?: ""
        }
    }

    /**
     * Gets the access token.
     * @return A flow of the access token.
     */
    fun getAccessToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[accessToken] ?: ""
        }
    }

    /**
     * Clears the user's preferences. (used on logout)
     */
    suspend fun clear() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

    /**
     * Gets the email of the user.
     * @return A flow of the email of the user.
     */
    fun getEmail(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[email] ?: ""
        }
    }

    /**
     * Stores the user's details in the DataStore.
     * @param user The user whose details are to be stored.
     */
    suspend fun storeUserDetails(user: User) {
        dataStore.edit { preferences ->
            preferences[userId] = user.userId ?: ""
            preferences[accessToken] = user.accessToken ?: ""
            preferences[email] = user.email
        }
    }

    /**
     * Stores the barber's details in the DataStore.
     * @param barberDetails The barber whose details are to be stored.
     */
    suspend fun storeBarberDetails(barberDetails: BarberDTO) {
        dataStore.edit { preferences ->
            preferences[firstName] = barberDetails.firstName
            preferences[lastName] = barberDetails.lastName
            preferences[yearsOfExperience] = barberDetails.yearsOfExperience
            preferences[role] = Constants.BarberRole
        }
    }

    /**
     * Stores the customer's details in the DataStore.
     * @param customerDetails The customer whose details are to be stored.
     */
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