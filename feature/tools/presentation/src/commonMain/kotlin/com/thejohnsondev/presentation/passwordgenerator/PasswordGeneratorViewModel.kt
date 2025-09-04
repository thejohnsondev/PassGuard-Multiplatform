package com.thejohnsondev.presentation.passwordgenerator

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.tools.PASSWORD_GENERATOR_DEFAULT_LENGTH
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.model.tools.PasswordGeneratorConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordGeneratorViewModel(
    private val updatePasswordGeneratorConfigUseCase: UpdatePasswordGeneratorConfigUseCase,
    private val getPasswordGeneratorConfigUseCase: GetPasswordGeneratorConfigUseCase,
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val copyTextUseCase: CopyTextUseCase,
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

    private var passwordGenerationJob: Job? = null

    fun perform(action: Action) {
        when (action) {
            is Action.FetchConfig -> fetchConfig()
            is Action.Copy -> copy(action.password)
            is Action.GeneratePassword -> launch { generatePassword() }
            is Action.Reset -> reset()
            is Action.UpdateIncludeDigits -> updateIncludeDigits(action.includeDigits)
            is Action.UpdateIncludeLower -> updateIncludeLower(action.includeLower)
            is Action.UpdateIncludeSpecial -> updateIncludeSpecial(action.includeSpecial)
            is Action.UpdateIncludeUpper -> updateIncludeUpper(action.includeUpper)
            is Action.UpdateLength -> updateLength(action.length)
            is Action.UpdateType -> updateType(action.type)
        }
    }

    private fun fetchConfig() = launch {
        val config = getPasswordGeneratorConfigUseCase()
        _state.value = state.value.copy(
            length = config.length,
            previousLengthValue = config.length,
            includeLower = config.includeLower,
            includeUpper = config.includeUpper,
            includeDigits = config.includeDigits,
            includeSpecial = config.includeSpecial,
            type = config.type
        )
        generatePassword()
    }


    private fun copy(password: String) {
        Analytics.trackEvent("copied_generated_password")
        copyTextUseCase(
            text = password,
            isSensitive = true
        )
    }

    private fun generatePassword() {
        passwordGenerationJob?.cancel()
        passwordGenerationJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = generatePasswordUseCase(
                    PasswordGeneratorConfig(
                        type = state.value.type,
                        length = state.value.length,
                        includeLower = state.value.includeLower,
                        includeUpper = state.value.includeUpper,
                        includeDigits = state.value.includeDigits,
                        includeSpecial = state.value.includeSpecial
                    )
                )
                withContext(Dispatchers.Main) {
                    _state.value = state.value.copy(passwordGeneratedResult = result)
                }
                sendEvent(OnPasswordGenerated(result))
            }
        }
    }

    private suspend fun updateConfig() {
        withContext(Dispatchers.IO) {
            updatePasswordGeneratorConfigUseCase(
                type = state.value.type,
                length = state.value.length,
                includeLower = state.value.includeLower,
                includeUpper = state.value.includeUpper,
                includeDigits = state.value.includeDigits,
                includeSpecial = state.value.includeSpecial
            )
        }
    }

    private fun reset() = launch {
        _state.value = state.value.copy(
            length = PASSWORD_GENERATOR_DEFAULT_LENGTH,
            includeLower = true,
            includeUpper = true,
            includeDigits = true,
            includeSpecial = true,
            type = PasswordGenerationType.RANDOM
        )
        updateConfig()
        generatePassword()
    }

    private fun updateIncludeDigits(include: Boolean) = launch {
        Analytics.trackEvent(
            name = "toggled_include_digits",
            props = mapOf(
                "include" to include
            )
        )
        _state.value = state.value.copy(includeDigits = include)
        updateConfig()
        generatePassword()
    }

    private fun updateIncludeLower(include: Boolean) = launch {
        Analytics.trackEvent(
            name = "toggled_include_lowercase",
            props = mapOf(
                "include" to include
            )
        )
        _state.value = state.value.copy(includeLower = include)
        updateConfig()
        generatePassword()
    }

    private fun updateIncludeSpecial(include: Boolean) = launch {
        Analytics.trackEvent(
            name = "toggled_include_special",
            props = mapOf(
                "include" to include
            )
        )
        _state.value = state.value.copy(includeSpecial = include)
        updateConfig()
        generatePassword()
    }

    private fun updateIncludeUpper(include: Boolean) = launch {
        Analytics.trackEvent(
            name = "toggled_include_uppercase",
            props = mapOf(
                "include" to include
            )
        )
        _state.value = state.value.copy(includeUpper = include)
        updateConfig()
        generatePassword()
    }

    private fun updateLength(length: Int) = launch {
        Analytics.trackEvent(
            name = "changed_password_length",
            props = mapOf(
                "length" to length
            )
        )
        val previousLength = state.value.length
        _state.value = state.value.copy(length = length, previousLengthValue = previousLength)
        generatePassword()
        updateConfig()
    }

    private fun updateType(type: PasswordGenerationType) = launch {
        Analytics.trackEvent(
            name = "changed_password_generation_type",
            props = mapOf(
                "type" to type.name
            )
        )
        _state.value = state.value.copy(type = type)
        updateConfig()
        generatePassword()
    }

    data class OnPasswordGenerated(
        val passwordGeneratedResult: PasswordGeneratedResult
    ): OneTimeEvent()

    sealed class Action {
        data object FetchConfig : Action()
        data object GeneratePassword : Action()
        data class UpdateLength(val length: Int) : Action()
        data class UpdateIncludeLower(val includeLower: Boolean) : Action()
        data class UpdateIncludeUpper(val includeUpper: Boolean) : Action()
        data class UpdateIncludeDigits(val includeDigits: Boolean) : Action()
        data class UpdateIncludeSpecial(val includeSpecial: Boolean) : Action()
        data class UpdateType(val type: PasswordGenerationType) : Action()
        data object Reset : Action()
        data class Copy(val password: String) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val passwordGeneratedResult: PasswordGeneratedResult? = null,
        val length: Int = PASSWORD_GENERATOR_DEFAULT_LENGTH,
        val previousLengthValue: Int = PASSWORD_GENERATOR_DEFAULT_LENGTH,
        val includeLower: Boolean = true,
        val includeUpper: Boolean = true,
        val includeDigits: Boolean = true,
        val includeSpecial: Boolean = true,
        val type: PasswordGenerationType = PasswordGenerationType.RANDOM,
    )
}