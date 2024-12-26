package com.thejohnsondev.presentation

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase

class SettingsViewModel(
    private val authService: AuthService,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
): BaseViewModel() {

    fun logout() = launch {
        authService.logout()
    }

    private fun deleteAccount() = launch {
        authService.deleteAccount()
    }

}