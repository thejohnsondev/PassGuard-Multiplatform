package com.thejohnsondev.presentation.additem

import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.EncryptPasswordModelUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.GeneratePasswordModelUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.domain.ValidatePasswordModelUseCase
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddVaultItemViewModel(
    private val passwordsService: PasswordsService,
    private val addAdditionalFieldUseCase: AddAdditionalFieldUseCase,
    private val enterAdditionalFieldTitleUseCase: EnterAdditionalFieldTitleUseCase,
    private val enterAdditionalFieldValueUseCase: EnterAdditionalFieldValueUseCase,
    private val removeAdditionalFieldUseCase: RemoveAdditionalFieldUseCase,
    private val generatePasswordModelUseCase: GeneratePasswordModelUseCase,
    private val encryptPasswordModelUseCase: EncryptPasswordModelUseCase,
    private val validatePasswordModelUseCase: ValidatePasswordModelUseCase
) : BaseViewModel() {

    private val _passwordId = MutableStateFlow<String?>(null)
    private val _organization = MutableStateFlow(String.Companion.empty)
    private val _title = MutableStateFlow(String.Companion.empty)
    private val _password = MutableStateFlow(String.Companion.empty)
    private val _additionalFields = MutableStateFlow<List<AdditionalFieldDto>>(emptyList())
    private val _createdTime = MutableStateFlow<String?>(null)
    private val _selectedCategoryId = MutableStateFlow(VAULT_ITEM_CATEGORY_PERSONAL)
    private val _isFavorite = MutableStateFlow(false)
    private val _isValid = MutableStateFlow(false)
    private val _isEdit = MutableStateFlow(false)

    val state = combine(
        _screenState,
        _organization,
        _title,
        _password,
        _additionalFields,
        _isFavorite,
        _selectedCategoryId,
        _isValid,
        _isEdit,
        ::State
    )

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
        }
    }

    private fun savePassword() = launchLoading {
        val passwordDto = generatePasswordModelUseCase(
            passwordId = _passwordId.value,
            organization = _organization.value,
            title = _title.value,
            password = _password.value,
            categoryId = _selectedCategoryId.value, // TODO add selecting category
            additionalFields = _additionalFields.value,
            createdTime = _createdTime.value,
            isFavorite = _isFavorite.value // TODO add making favorite
        )
        val encryptedPasswordDto = encryptPasswordModelUseCase(passwordDto)
        passwordsService.createOrUpdatePassword(encryptedPasswordDto)
        sendEvent(OneTimeEvent.SuccessNavigation)
    }

    private fun setPasswordForEdit(passwordUIModel: PasswordUIModel) = launch {
        _passwordId.emit(passwordUIModel.id)
        _organization.emit(passwordUIModel.organization)
        _title.emit(passwordUIModel.title)
        _password.emit(passwordUIModel.password)
        _additionalFields.emit(passwordUIModel.additionalFields)
        _createdTime.emit(passwordUIModel.createdTime)
        _isEdit.emit(true)
        validateFields()
    }

    private fun enterOrganization(organization: String) = launch {
        _organization.emit(organization)
        validateFields()
    }

    private fun enterTitle(title: String) = launch {
        _title.emit(title)
        validateFields()
    }

    private fun enterPassword(password: String) = launch {
        _password.emit(password)
        validateFields()
    }

    private fun addAdditionalField() = launch {
        val updatedList = addAdditionalFieldUseCase(_additionalFields.value)
        _additionalFields.emit(updatedList)
        validateFields()
    }

    private fun enterAdditionalFieldTitle(id: String, title: String) = launch {
        val updatedList = enterAdditionalFieldTitleUseCase(id, title, _additionalFields.value)
        _additionalFields.emit(updatedList)
        validateFields()
    }

    private fun enterAdditionalFieldValue(id: String, value: String) = launch {
        val updatedList = enterAdditionalFieldValueUseCase(id, value, _additionalFields.value)
        _additionalFields.emit(updatedList)
        validateFields()
    }

    private fun removeAdditionalField(id: String) = launch {
        val updatedList = removeAdditionalFieldUseCase(id, _additionalFields.value)
        _additionalFields.emit(updatedList)
        validateFields()
    }

    private suspend fun validateFields() {
        val isValid = validatePasswordModelUseCase(
            organization = _organization.value,
            title = _title.value,
            password = _password.value,
            additionalFieldsList = _additionalFields.value
        )
        _isValid.emit(isValid)
    }

    fun clear() = launch {
        _passwordId.emit(null)
        _screenState.emit(ScreenState.None)
        _organization.emit(String.Companion.empty)
        _title.emit(String.Companion.empty)
        _password.emit(String.Companion.empty)
        _additionalFields.emit(emptyList())
        _createdTime.emit(null)
        _selectedCategoryId.emit(VAULT_ITEM_CATEGORY_PERSONAL)
        _isFavorite.emit(false)
        _isValid.emit(false)
        _isEdit.emit(false)
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
        val selectedCategoryId: String = VAULT_ITEM_CATEGORY_PERSONAL,
        val isValid: Boolean = false,
        val isEdit: Boolean = false,
    )

}