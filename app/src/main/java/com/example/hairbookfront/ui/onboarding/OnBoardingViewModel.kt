package com.example.hairbookfront.ui.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.domain.usecases.app_entry.AppEntryUseCases
import com.example.hairbookfront.util.Constants.ONBOARDING_VIEW_MODEL_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {


    private val _pagesState = MutableStateFlow<List<Page>>(pages)
    val pagesState: StateFlow<List<Page>>
        get() = _pagesState

    private val _buttonsState = MutableStateFlow(calculateButtonText(0))
    val buttonsState: StateFlow<List<String>> = _buttonsState

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.SaveAppEntry -> {
                Log.d(ONBOARDING_VIEW_MODEL_TAG, "SaveAppEntry: saved ")
                saveAppEntry()
            }
            is OnBoardingEvent.ChangePage -> {
                _buttonsState.value = calculateButtonText(event.currentPage)
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

    private fun calculateButtonText(currentPage: Int): List<String> {
        return when (currentPage) {
            0 -> listOf("", "Next")
            1 -> listOf("Back", "Next")
            2 -> listOf("Back", "Get Started")
            else -> listOf("", "")
        }
    }


}