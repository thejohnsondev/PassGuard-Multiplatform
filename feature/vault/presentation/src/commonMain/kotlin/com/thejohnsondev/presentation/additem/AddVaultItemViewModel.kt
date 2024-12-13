package com.thejohnsondev.presentation.additem

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class AddVaultItemViewModel : BaseViewModel() {

    private val _organization = MutableStateFlow(String.Companion.empty)
    private val _isEdit = MutableStateFlow(false)

    val state = combine(
        _screenState,
        _organization,
        _isEdit,
        ::State
    )

    fun perform(action: Action) {
        when (action) {
            is Action.SetPasswordForEdit -> setPasswordForEdit(action.passwordUIModel)
            is Action.EnterOrganization -> enterOrganization(action.organization)
        }
    }

    private fun setPasswordForEdit(passwordUIModel: PasswordUIModel) = launch {
        _organization.emit(passwordUIModel.organization)
        _isEdit.emit(true)
    }

    private fun enterOrganization(organization: String) = launch {
        _organization.emit(organization)
    }

    fun clear() = launch {
        _organization.emit(String.Companion.empty)
        _isEdit.emit(false)
    }

    sealed class Action {
        class SetPasswordForEdit(val passwordUIModel: PasswordUIModel) : Action()
        class EnterOrganization(val organization: String) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val organization: String = String.Companion.empty,
        val isEdit: Boolean = false
    )

}