package com.example.hairbookfront.ui.shared.readReviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the ReadReviews screen.
 *
 * @property signOutHandler The handler for signing out.
 * @property hairBookRepositoryReview The repository for review related operations.
 * @property hairBookRepositoryBarber The repository for barber related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class ReadReviewsViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _reviewToDelete = MutableStateFlow("")

    private val _role = MutableStateFlow("")
    val role: StateFlow<String>
        get() = _role

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String>
        get() = _userId

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _lastScreen = MutableStateFlow(false)
    val lastScreen: StateFlow<Boolean>
        get() = _lastScreen

    private val _reviews = MutableStateFlow<List<Review>?>(listOf())

    val reviews: StateFlow<List<Review>?>
        get() = _reviews


    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _role.value = dataStorePreferences.getRole().first()
        }
        getReviews()
    }

    fun clearScreen() {
        _screen.value = ""
    }

    fun onBackClicked() {
        _lastScreen.value = true
    }

    fun editReview(reviewId: String) {
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.EditMode)
            dataStorePreferences.setReviewIdForEditing(reviewId)
            _screen.emit(Routes.EditOrCreateReviewScreen.route)
        }
    }

    private val _showOrHideDeleteDialog = MutableStateFlow(false)
    val showOrHideDeleteDialog: StateFlow<Boolean>
        get() = _showOrHideDeleteDialog

    fun showOrHideDeleteDialog(reviewId: String) {
        _reviewToDelete.value = reviewId
        _showOrHideDeleteDialog.value = !_showOrHideDeleteDialog.value
    }


    fun profileClicked() {
        if (_role.value == Constants.BarberRole)
            _screen.value = Routes.BarberDetailsScreen.route
        else
            _screen.value = Routes.CustomerDetailsScreen.route
    }

    fun expandedFun() {
        _isExpanded.value = !_isExpanded.value
    }

    fun dismissMenu() {
        _isExpanded.value = false
    }

    fun signOut() {
        viewModelScope.launch {
            signOutHandler.signOut(_accessToken.value)
            _screen.emit(Routes.WelcomeScreen.route)
        }
    }

    private fun getReviews() {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            hairBookRepositoryReview.getMyReviews(_accessToken.value)
                .collectLatest { resourceState ->
                    when (resourceState) {
                        is ResourceState.SUCCESS -> {
                            Timber.d("Reviews: ${resourceState.data}")
                            _reviews.emit(resourceState.data)
                        }

                        is ResourceState.ERROR -> {
                            Timber.d(resourceState.error)
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }
                    }
                }
        }
    }

    fun onDismissRequest() {
        _showOrHideDeleteDialog.value = false
    }

    fun deleteReview() {
        viewModelScope.launch {
            hairBookRepositoryReview.deleteReview(_accessToken.value, _reviewToDelete.value)
                .collectLatest { response ->
                    Timber.d("res: $response")
                    when (response) {
                        is ResourceState.LOADING -> {
                        }

                        is ResourceState.SUCCESS -> {
                            _showOrHideDeleteDialog.emit(false)
                            _reviews.value =
                                _reviews.value?.filter { it.reviewId != _reviewToDelete.value }
                            getReviews()
                        }

                        is ResourceState.ERROR -> {
                        }
                    }
                }
        }
    }
}