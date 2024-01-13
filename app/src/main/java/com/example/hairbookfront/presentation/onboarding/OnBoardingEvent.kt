package com.example.hairbookfront.presentation.onboarding

sealed class OnBoardingEvent {

    object SaveAppEntry : OnBoardingEvent()

    data class ChangePage(val currentPage: Int) : OnBoardingEvent()
}