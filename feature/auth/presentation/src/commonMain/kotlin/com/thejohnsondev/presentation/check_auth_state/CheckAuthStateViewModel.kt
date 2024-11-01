package com.thejohnsondev.presentation.check_auth_state

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class CheckAuthStateViewModel(
    private val getFirstScreenRoute: GetFirstScreenRouteUseCase
) : BaseViewModel() {

    val firstScreenRoute = MutableStateFlow<String?>(null)

    init {
        fetchFirstScreenRoute()
    }

    private fun fetchFirstScreenRoute() = launch {
        firstScreenRoute.emit(getFirstScreenRoute())
    }

}