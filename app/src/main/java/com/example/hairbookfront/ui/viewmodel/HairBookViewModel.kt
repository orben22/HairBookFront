package com.example.hairbookfront.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.AppConstants
import com.example.hairbookfront.data.entity.NewsResponse
import com.example.hairbookfront.ui.repository.HairBookRepository
import com.example.utilities.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HairBookViewModel @Inject constructor(
    private val hairBookRepository: HairBookRepository
) : ViewModel() {

    private val _news: MutableStateFlow<ResourceState<NewsResponse>> =
        MutableStateFlow(ResourceState.LOADING())
    val news: StateFlow<ResourceState<NewsResponse>>
        get() = _news

    init {
        getNews(AppConstants.Country)
    }

    private fun getNews(country: String) {
        Log.d(TAG, "getNews: ")
        viewModelScope.launch(Dispatchers.IO) {
            hairBookRepository.getNewsHeadline(country).collectLatest { newsResponse ->
                Log.d(TAG, "getNews: $newsResponse")
                _news.value = newsResponse
            }
        }
    }

    //static
    companion object {
        const val TAG = "HairBookViewModel"
    }
}