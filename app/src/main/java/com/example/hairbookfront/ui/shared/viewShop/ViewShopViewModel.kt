package com.example.hairbookfront.ui.shared.viewShop

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ViewShopViewModel @Inject constructor(
    private val hairBookRepository: ApiRepositoryAuth
) : ViewModel() {
    private val _barberShopName = MutableStateFlow("")
    val barberShopName: StateFlow<String>
        get() = _barberShopName

    private val _openDays = MutableStateFlow(listOf(0, 0, 0, 0, 0, 0, 0))
    val openDays: MutableStateFlow<List<Int>>
        get() = _openDays

    private val _sundayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val sundayHours: MutableStateFlow<List<String>>
        get() = _sundayHours

    private val _mondayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val mondayHours: MutableStateFlow<List<String>>
        get() = _mondayHours

    private val _tuesdayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val tuesdayHours: MutableStateFlow<List<String>>
        get() = _tuesdayHours

    private val _wednesdayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val wednesdayHours: MutableStateFlow<List<String>>
        get() = _wednesdayHours

    private val _thursdayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val thursdayHours: MutableStateFlow<List<String>>
        get() = _thursdayHours

    private val _fridayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val fridayHours: MutableStateFlow<List<String>>
        get() = _fridayHours

    private val _saturdayHours = MutableStateFlow(listOf("10:00", "10:30", "11:00"))
    val saturdayHours: MutableStateFlow<List<String>>
        get() = _saturdayHours


}