package com.example.hairbookfront.domain

import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles the sign out operations.
 *
 * @property dataStorePreferences The datastore for storing preferences.
 * @property apiRepository The repository for authentication related operations.
 */
@Singleton
class SignOutHandler @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val apiRepository: ApiRepositoryAuth,
) {

    /**
     * Signs out the user.
     *
     * @param accessToken The access token of the user.
     * @return A boolean indicating whether the sign out was successful.
     */
    suspend fun signOut(accessToken: String): Boolean {
        var signOutSuccessful = false
        apiRepository.signOut(accessToken).collectLatest { response ->
            when (response) {
                is ResourceState.SUCCESS -> {
                    dataStorePreferences.clear()
                    signOutSuccessful = true
                }

                is ResourceState.ERROR -> {
                    Timber.d("Error: ${response.error}")
                }

                is ResourceState.LOADING -> {
                    Timber.d("logout: loading")
                }
            }
        }
        return signOutSuccessful
    }
}