package com.thejohnsondev.presentation

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AuthService

class SettingsViewModel(
    private val authService: AuthService
): BaseViewModel() {

    fun logout() = launch {
        authService.logout()
    }

}