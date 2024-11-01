package com.thejohnsondev.presentation.vault

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AuthService

class VaultViewModel(
    private val authService: AuthService
) : BaseViewModel() {

    fun logout() = launch {
        authService.logout()
    }

}