package com.example.hairbookfront.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.domain.model.NewsResponse
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.Constants.HOME_VIEW_MODEL_TAG
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepository
) : ViewModel() {

    private val _news: MutableStateFlow<ResourceState<NewsResponse>> =
        MutableStateFlow(ResourceState.LOADING())
    val news: StateFlow<ResourceState<NewsResponse>>
        get() = _news

    init {
        getNews(Constants.COUNTRY)
    }

    private fun getNews(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            hairBookRepository.getNewsHeadline(country).collectLatest { newsResponse ->
                Log.d(HOME_VIEW_MODEL_TAG, "getNews: $newsResponse")
                _news.value = newsResponse
            }
        }
    }
}
