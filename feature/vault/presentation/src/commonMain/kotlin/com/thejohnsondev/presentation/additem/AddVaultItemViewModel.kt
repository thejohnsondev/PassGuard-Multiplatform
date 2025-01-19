package com.thejohnsondev.presentation.additem

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.empty
import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.EncryptPasswordModelUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.GeneratePasswordModelUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.domain.ValidatePasswordModelUseCase
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.model.CategoryUIModel
import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AddVaultItemViewModel(
    private val passwordsService: PasswordsService,
    private val addAdditionalFieldUseCase: AddAdditionalFieldUseCase,
    private val enterAdditionalFieldTitleUseCase: EnterAdditionalFieldTitleUseCase,
    private val enterAdditionalFieldValueUseCase: EnterAdditionalFieldValueUseCase,
    private val removeAdditionalFieldUseCase: RemoveAdditionalFieldUseCase,
    private val generatePasswordModelUseCase: GeneratePasswordModelUseCase,
    private val encryptPasswordModelUseCase: EncryptPasswordModelUseCase,
    private val validatePasswordModelUseCase: ValidatePasswordModelUseCase,
) : BaseViewModel() {

    private val _passwordId = MutableStateFlow<String?>(null)
    private val _createdTime = MutableStateFlow<String?>(null)

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
    ) { screenState, state ->
        state.copy(
            screenState = screenState
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun perform(action: Action) {
        when (action) {
            is Action.SetPasswordForEdit -> setPasswordForEdit(action.passwordUIModel)
            is Action.EnterOrganization -> enterOrganization(action.organization)
            is Action.EnterPassword -> enterPassword(action.password)
            is Action.EnterTitle -> enterTitle(action.title)
            is Action.AddAdditionalField -> addAdditionalField()
            is Action.EnterAdditionalFieldTitle -> enterAdditionalFieldTitle(
                action.id,
                action.title
            )

            is Action.EnterAdditionalFieldValue -> enterAdditionalFieldValue(
                action.id,
                action.value
            )

            is Action.RemoveAdditionalField -> removeAdditionalField(action.id)
            is Action.SavePassword -> savePassword()
            is Action.Clear -> clear()
            is Action.SelectCategory -> selectCategory(action.category)
        }
    }

    private fun selectCategory(category: CategoryUIModel) = launch {
        _state.update { it.copy(selectedCategory = category) }
    }

    private fun savePassword() = launchLoading {
        val selectedCategoryId = _state.value.selectedCategory.id
        val passwordDto = generatePasswordModelUseCase(
            passwordId = _passwordId.value,
            organization = _state.value.organization,
            title = _state.value.title,
            password = _state.value.password,
            categoryId = selectedCategoryId,
            additionalFields = _state.value.additionalFields,
            createdTime = _createdTime.value,
            isFavorite = _state.value.isFavorite // TODO add making favorite
        )
        val encryptedPasswordDto = encryptPasswordModelUseCase(passwordDto)
        passwordsService.createOrUpdatePassword(encryptedPasswordDto)
        delay(SAVE_ANIMATE_TIME)
        sendEvent(
            OneTimeEvent.SuccessNavigation(
                if (_state.value.isEdit) DisplayableMessageValue.PasswordEditSuccess else DisplayableMessageValue.PasswordAddedSuccess
            )
        )
    }

    private fun setPasswordForEdit(passwordUIModel: PasswordUIModel) = launch {
        _passwordId.emit(passwordUIModel.id)
        _createdTime.emit(passwordUIModel.createdTime)
        _state.update {
            it.copy(
                isEdit = true,
                organization = passwordUIModel.organization,
                title = passwordUIModel.title,
                password = passwordUIModel.password,
                additionalFields = passwordUIModel.additionalFields,
                selectedCategory = passwordUIModel.category
            )
        }
        validateFields()
    }

    private fun enterOrganization(organization: String) = launch {
        _state.update {
            it.copy(organization = organization)
        }
        validateFields()
    }

    private fun enterTitle(title: String) = launch {
        _state.update {
            it.copy(title = title)
        }
        validateFields()
    }

    private fun enterPassword(password: String) = launch {
        _state.update {
            it.copy(password = password)
        }
        validateFields()
    }

    private fun addAdditionalField() = launch {
        val updatedList = addAdditionalFieldUseCase(_state.value.additionalFields)
        _state.update {
            it.copy(additionalFields = updatedList)
        }
        validateFields()
    }

    private fun enterAdditionalFieldTitle(id: String, title: String) = launch {
        val updatedList = enterAdditionalFieldTitleUseCase(id, title, _state.value.additionalFields)
        _state.update {
            it.copy(additionalFields = updatedList)
        }
        validateFields()
    }

    private fun enterAdditionalFieldValue(id: String, value: String) = launch {
        val updatedList = enterAdditionalFieldValueUseCase(id, value, _state.value.additionalFields)
        _state.update {
            it.copy(additionalFields = updatedList)
        }
        validateFields()
    }

    private fun removeAdditionalField(id: String) = launch {
        val updatedList = removeAdditionalFieldUseCase(id, _state.value.additionalFields)
        _state.update {
            it.copy(additionalFields = updatedList)
        }
        validateFields()
    }

    private suspend fun validateFields() {
        val isValid = validatePasswordModelUseCase(
            organization = _state.value.organization,
            title = _state.value.title,
            password = _state.value.password,
            additionalFieldsList = _state.value.additionalFields
        )
        _state.update {
            it.copy(isValid = isValid)
        }
    }

    fun clear() = launch {
        _passwordId.emit(null)
        screenState.emit(ScreenState.None)
        _createdTime.emit(null)
        _state.update { State() }
    }

    sealed class Action {
        data class SetPasswordForEdit(val passwordUIModel: PasswordUIModel) : Action()
        data class EnterOrganization(val organization: String) : Action()
        data class EnterTitle(val title: String) : Action()
        data class EnterPassword(val password: String) : Action()
        data object AddAdditionalField : Action()
        data class EnterAdditionalFieldTitle(val id: String, val title: String) : Action()
        data class EnterAdditionalFieldValue(val id: String, val value: String) : Action()
        data class RemoveAdditionalField(val id: String) : Action()
        data class SelectCategory(val category: CategoryUIModel) : Action()
        data object SavePassword : Action()
        data object Clear : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val organization: String = String.Companion.empty,
        val title: String = String.Companion.empty,
        val password: String = String.Companion.empty,
        val additionalFields: List<AdditionalFieldDto> = emptyList(),
        val isFavorite: Boolean = false,
        val selectedCategory: CategoryUIModel = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory(),
        val itemCategoryFilters: List<FilterUIModel> = FiltersProvider.Category.getVaultCategoryFilters(),
        val isValid: Boolean = false,
        val isEdit: Boolean = false,
    )

    companion object {
        private const val SAVE_ANIMATE_TIME = 300L
    }

}