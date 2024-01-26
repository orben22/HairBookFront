package com.example.hairbookfront.domain

import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignOutHandler @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val apiRepository: ApiRepositoryAuth,
) {

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