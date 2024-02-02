package com.example.hairbookfront.ui.shared.editOrCreateReview


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.Constants.DATE_FORMAT
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditOrCreateReviewViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _role = MutableStateFlow("")
    private val _sentReview = MutableStateFlow(
        Review(
            reviewId = null,
            firstName = "",
            lastName = "",
            review = "",
            rating = 0.0f,
            timestamp = "",
            userId = "",
            barberShopId = ""
        )
    )
    private val reviewToEditId = MutableStateFlow("")

    private val _mode = MutableStateFlow("")
    val mode: StateFlow<String>
        get() = _mode
    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String>
        get() = _firstName
    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

    private val _userId = MutableStateFlow("")

    private val _shopId = MutableStateFlow("")
    val shopId: StateFlow<String>
        get() = _shopId
    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen


    private val _review = MutableStateFlow("")
    val review: StateFlow<String>
        get() = _review
    private val _rating = MutableStateFlow("")
    val rating: StateFlow<String>
        get() = _rating
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun expandedFun() {
        _isExpanded.value = !_isExpanded.value
    }

    fun onReviewChange(newReview: String) {
        _review.value = newReview
    }

    fun onRatingChange(newRating: String) {
        Timber.d("newRating: $newRating")
        if (newRating.isEmpty()) {
            _rating.value = ""
            _isError.value = false
        } else if (newRating.toFloat() in 0.0..5.0) {
            _rating.value = newRating
            _isError.value = false
        } else {
            _isError.value = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmChanges() {
        val review = _review.value
        if (review.isBlank() || rating.value.toFloat() !in 0.0..5.0) {
            _isError.value = true
        } else {
            _isError.value = false
            val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT))
            val newReview = Review(
                reviewId = null,
                firstName = firstName.value,
                lastName = lastName.value,
                review = review,
                rating = rating.value.toFloat(),
                timestamp = currentDate,
                userId = _userId.value,
                barberShopId = shopId.value
            )
            viewModelScope.launch(Dispatchers.IO) {
                if (_mode.value == Constants.EditMode) {
                    updateReview(newReview)
                } else {
                    postReview(newReview)
                }
            }

        }
    }

    private suspend fun updateReview(newReview: Review) {
        hairBookRepositoryReview.updateReview(
            _accessToken.value, reviewToEditId.value, newReview
        ).collectLatest { response ->
            when (response) {
                is ResourceState.SUCCESS -> {
                    sendMessage("Review updated")
                    _sentReview.emit(response.data)
                    Timber.d("Review sent: ${_sentReview.value}")
                    _screen.emit(Routes.ViewShopScreen.route)
                }

                is ResourceState.ERROR -> {
                    Timber.d(response.error)
                }

                is ResourceState.LOADING -> {
                    Timber.d("Loading")
                }
            }
        }
    }

    private suspend fun postReview(newReview: Review) {
        hairBookRepositoryReview.postReview(
            _accessToken.value, newReview
        ).collectLatest { response ->
            when (response) {
                is ResourceState.SUCCESS -> {
                    sendMessage("Review sent")
                    _sentReview.emit(response.data)
                    Timber.d("Review sent: ${_sentReview.value}")
                    _screen.emit(Routes.ViewShopScreen.route)
                }

                is ResourceState.ERROR -> {
                    Timber.d(response.error)
                }

                is ResourceState.LOADING -> {
                    Timber.d("Loading")
                }
            }
        }
    }

    init {
        getInfo()
    }

    private fun getReviewById() {
        viewModelScope.launch {
            reviewToEditId.value = dataStorePreferences.getReviewIdForEditing().first()
            hairBookRepositoryReview.getReviewById(_accessToken.value, reviewToEditId.value)
                .collectLatest { response ->
                    when (response) {
                        is ResourceState.SUCCESS -> {
                            _sentReview.emit(response.data)
                            _review.emit(response.data.review)
                            _rating.emit(response.data.rating.toString())
                            _firstName.emit(response.data.firstName)
                            _lastName.emit(response.data.lastName)
                            Timber.d("Review sent: ${_sentReview.value}")
                        }

                        is ResourceState.ERROR -> {
                            Timber.d(response.error)
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }
                    }
                }
        }
    }

    private fun getInfo() {
        viewModelScope.launch {
            _mode.emit(dataStorePreferences.getMode().first())
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            _shopId.emit(dataStorePreferences.getShopId().first())

            _firstName.emit(dataStorePreferences.getFirstName().first())
            _lastName.emit(dataStorePreferences.getLastName().first())

            _userId.emit(dataStorePreferences.getUserId().first())
            _role.emit(dataStorePreferences.getRole().first())
            _mode.emit(dataStorePreferences.getMode().first())
            if (_mode.value == Constants.EditMode) {
                getReviewById()
            }
        }
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


    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}