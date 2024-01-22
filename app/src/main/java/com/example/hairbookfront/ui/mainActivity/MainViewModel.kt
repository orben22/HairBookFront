package com.example.hairbookfront.ui.mainActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.hairbookfront.ui.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    var startDestination by mutableStateOf(Routes.AuthGraph.route)
        private set

    init {
        startDestination = Routes.AuthGraph.route
        _splashCondition.value = false
    }
}