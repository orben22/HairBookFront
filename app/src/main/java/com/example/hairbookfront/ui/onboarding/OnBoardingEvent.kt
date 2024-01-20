package com.example.hairbookfront.ui.onboarding

sealed class OnBoardingEvent {

    object SaveAppEntry : OnBoardingEvent()

    data class ChangePage(val currentPage: Int) : OnBoardingEvent()
}