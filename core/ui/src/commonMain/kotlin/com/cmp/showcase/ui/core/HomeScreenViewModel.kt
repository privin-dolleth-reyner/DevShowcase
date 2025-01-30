package com.cmp.showcase.ui.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenViewModel: ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state

    fun setSelectedNav(nav: BottomNavigation){
        _state.value = _state.value.copy(selectedNav = nav)
    }
}

data class HomeScreenState(
    val selectedNav: BottomNavigation = BottomNavigation.Home
)