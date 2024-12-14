package com.thejohnsondev.presentation.additem

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddVaultItemViewModel(
    private val addAdditionalFieldUseCase: AddAdditionalFieldUseCase,
    private val enterAdditionalFieldTitleUseCase: EnterAdditionalFieldTitleUseCase,
    private val enterAdditionalFieldValueUseCase: EnterAdditionalFieldValueUseCase,
    private val removeAdditionalFieldUseCase: RemoveAdditionalFieldUseCase
) : BaseViewModel() {

    private val _passwordId = MutableStateFlow(String.Companion.empty)
    private val _organization = MutableStateFlow(String.Companion.empty)
    private val _title = MutableStateFlow(String.Companion.empty)
    private val _password = MutableStateFlow(String.Companion.empty)
    private val _additionalFields = MutableStateFlow<List<AdditionalFieldDto>>(emptyList())
    private val _isEdit = MutableStateFlow(false)

    val state = combine(
        _screenState,
        _organization,
        _title,
        _password,
        _additionalFields,
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
        }
    }

    private fun setPasswordForEdit(passwordUIModel: PasswordUIModel) = launch {
        _passwordId.emit(passwordUIModel.id)
        _organization.emit(passwordUIModel.organization)
        _title.emit(passwordUIModel.title)
        _password.emit(passwordUIModel.password)
        _additionalFields.emit(passwordUIModel.additionalFields)
        _isEdit.emit(true)
    }

    private fun enterOrganization(organization: String) = launch {
        _organization.emit(organization)
    }

    private fun enterTitle(title: String) = launch {
        _title.emit(title)
    }

    private fun enterPassword(password: String) = launch {
        _password.emit(password)
    }

    private fun addAdditionalField() = launch {
        val updatedList = addAdditionalFieldUseCase(_additionalFields.value)
        _additionalFields.emit(updatedList)
    }

    private fun enterAdditionalFieldTitle(id: String, title: String) = launch {
        val updatedList = enterAdditionalFieldTitleUseCase(id, title, _additionalFields.value)
        _additionalFields.emit(updatedList)
    }

    private fun enterAdditionalFieldValue(id: String, value: String) = launch {
        val updatedList = enterAdditionalFieldValueUseCase(id, value, _additionalFields.value)
        _additionalFields.emit(updatedList)
    }

    private fun removeAdditionalField(id: String) = launch {
        val updatedList = removeAdditionalFieldUseCase(id, _additionalFields.value)
        _additionalFields.emit(updatedList)
    }

    fun clear() = launch {
        _screenState.emit(ScreenState.None)
        _organization.emit(String.Companion.empty)
        _title.emit(String.Companion.empty)
        _password.emit(String.Companion.empty)
        _additionalFields.emit(emptyList())
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
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val organization: String = String.Companion.empty,
        val title: String = String.Companion.empty,
        val password: String = String.Companion.empty,
        val additionalFields: List<AdditionalFieldDto> = emptyList(),
        val isEdit: Boolean = false
    )

}