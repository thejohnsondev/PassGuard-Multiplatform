package com.thejohnsondev.presentation.vaulthealth

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.PASSWORD_AGE_THRESHOLD_DAYS
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.GenerateVaultHealthReportUseCases
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.vaulthealth.VaultHealthReport
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class VaultHealthViewModel(
    private val passwordsService: PasswordsService,
    private val generateVaultHealthReportUseCases: GenerateVaultHealthReportUseCases,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(
        viewModelScope, SharingStarted.Eagerly,
        State()
    )

    fun perform(action: Action) {
        when (action) {
            is Action.GenerateReport -> generateReport()
        }
    }

    private fun generateReport() = launch {
        val allPasswords = passwordsService.getUserPasswords().first()
        val decryptedPasswords = decryptPasswordsListUseCase(allPasswords)
        val report = generateVaultHealthReportUseCases(
            passwords = decryptedPasswords,
            passwordAgeThresholdDays = PASSWORD_AGE_THRESHOLD_DAYS
        )
        _state.update {
            it.copy(
                report = report
            )
        }
    }

    sealed class Action {
        data object GenerateReport : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val report: VaultHealthReport? = null,
    )
}