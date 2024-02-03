package com.example.hairbookfront.ui.customer.reviewsHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import com.example.hairbookfront.ui.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the ReviewsHistory screen.
 *
 * @property signOutHandler The handler for signing out.
 * @property hairBookRepositoryReview The repository for review related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class ReviewsHistoryViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {
    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    fun expandedFun() {
        _isExpanded.value = !_isExpanded.value
    }

    fun dismissMenu() {
        _isExpanded.value = false
    }

    fun profileClicked() {
        _screen.value = Routes.CustomerDetailsScreen.route
    }

    fun signOut() {
        viewModelScope.launch {
            signOutHandler.signOut(_accessToken.value)
            _screen.emit(Routes.WelcomeScreen.route)
        }
    }


}