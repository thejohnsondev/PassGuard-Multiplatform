package com.thejohnsondev.presentation.additem

import androidx.compose.runtime.mutableStateOf
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

    private val _enteredTitle = mutableStateOf(String.empty)
    val enteredTitle = _enteredTitle

    private val _enteredUserName = mutableStateOf(String.empty)
    val enteredUserName = _enteredUserName

    private val _enteredPassword = mutableStateOf(String.empty)
    val enteredPassword = _enteredPassword

    private val _additionalFields = mutableStateOf<List<AdditionalFieldDto>>(emptyList())
    val additionalFields = _additionalFields

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
            is Action.EnterTitle -> enterTitle(action.title)
            is Action.EnterPassword -> enterPassword(action.password)
            is Action.EnterUserName -> enterUserName(action.userName)
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
        if (!state.value.isValid) {
            showContent()
            return@launchLoading
        }
        val selectedCategoryId = _state.value.selectedCategory.id
        val passwordDto = generatePasswordModelUseCase(
            passwordId = _passwordId.value,
            title = _enteredTitle.value,
            userName = _enteredUserName.value,
            password = _enteredPassword.value,
            categoryId = selectedCategoryId,
            additionalFields = _additionalFields.value,
            createdTime = _createdTime.value,
            isFavorite = _state.value.isFavorite
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
        _enteredTitle.value = passwordUIModel.title
        _enteredUserName.value = passwordUIModel.userName
        _enteredPassword.value = passwordUIModel.password
        _additionalFields.value = passwordUIModel.additionalFields
        _state.update {
            it.copy(
                isEdit = true,
                selectedCategory = passwordUIModel.category,
                isFavorite = passwordUIModel.isFavorite
            )
        }
        validateFields()
    }

    private fun enterTitle(title: String)  {
        _enteredTitle.value = title
        validateFields()
    }

    private fun enterUserName(userName: String) {
        _enteredUserName.value = userName
        validateFields()
    }

    private fun enterPassword(password: String)  {
        _enteredPassword.value = password
        validateFields()
    }

    private fun addAdditionalField() = launch {
        val updatedList = addAdditionalFieldUseCase(_additionalFields.value)
        _additionalFields.value = updatedList
        validateFields()
    }

    private fun enterAdditionalFieldTitle(id: String, title: String) = launch {
        val updatedList = enterAdditionalFieldTitleUseCase(id, title, _additionalFields.value)
        _additionalFields.value = updatedList
        validateFields()
    }

    private fun enterAdditionalFieldValue(id: String, value: String) = launch {
        val updatedList = enterAdditionalFieldValueUseCase(id, value, _additionalFields.value)
        _additionalFields.value = updatedList
        validateFields()
    }

    private fun removeAdditionalField(id: String) = launch {
        val updatedList = removeAdditionalFieldUseCase(id, _additionalFields.value)
        _additionalFields.value = updatedList
        validateFields()
    }

    private fun validateFields() = launch {
        val isValid = validatePasswordModelUseCase(
            title = _enteredTitle.value,
            userName = _enteredUserName.value,
            password = _enteredPassword.value,
            additionalFieldsList = _additionalFields.value
        )
        _state.update {
            it.copy(isValid = isValid)
        }
    }

    fun clear() = launch {
        screenState.emit(ScreenState.None)
        _passwordId.emit(null)
        _createdTime.emit(null)
        _enteredTitle.value = String.empty
        _enteredUserName.value = String.empty
        _enteredPassword.value = String.empty
        _additionalFields.value = emptyList()
        _state.update { State() }
    }

    sealed class Action {
        data class SetPasswordForEdit(val passwordUIModel: PasswordUIModel) : Action()
        data class EnterTitle(val title: String) : Action()
        data class EnterUserName(val userName: String) : Action()
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