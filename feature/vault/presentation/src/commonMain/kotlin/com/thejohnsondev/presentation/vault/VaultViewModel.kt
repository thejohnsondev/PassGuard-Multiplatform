package com.thejohnsondev.presentation.vault

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.models.PasswordUIModel
import com.thejohnsondev.model.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class VaultViewModel(
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase
) : BaseViewModel() {

    private val _allPasswordsList = MutableStateFlow<List<PasswordUIModel>>(emptyList())
    private val _passwordsList = MutableStateFlow<List<PasswordUIModel>>(emptyList())
    private val _isSearching = MutableStateFlow(false)
    private val _isDeepSearchingEnabled = MutableStateFlow(false)
    private val _showHideConfirmDelete =
        MutableStateFlow<Pair<Boolean, PasswordUIModel?>>(Pair(false, null))

    val state = combine(
        _loadingState,
        _passwordsList,
        _isSearching,
        _isDeepSearchingEnabled, // TODO replace with setting from settings config
        _showHideConfirmDelete,
        ::State
    )

    fun perform(action: Action) {
        when (action) {
            is Action.FetchVault -> fetchVault()
            is Action.DeletePassword -> deletePassword(action.password)
            is Action.Search -> search(action.query, action.isDeepSearchEnabled)
            is Action.StopSearching -> stopSearching()
            is Action.ShowHideConfirmDelete -> showHideConfirmDelete(action.deletePasswordPair)
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
        }
    }

    private fun fetchVault() = launch {
        val items = PasswordUIModel.testPasswordItems
        _passwordsList.emit(items)
        _allPasswordsList.emit(items)
    }

    private fun deletePassword(password: PasswordUIModel) {
        // TODO implement
    }

    private fun search(query: String, isDeepSearchEnabled: Boolean) {
        // TODO implement
    }

    private fun stopSearching() {
        // TODO implement
    }

    private fun showHideConfirmDelete(deletePasswordPair: Pair<Boolean, PasswordUIModel?>) {
        // TODO implement
    }

    private fun toggleOpenItem(newOpenedItemId: String?) = launch {
        val updatedList = toggleOpenedItemUseCase(
            newOpenedItemId,
            _passwordsList.value
        )
        _passwordsList.emit(updatedList)
    }


    sealed class Action {
        data object FetchVault : Action()
        data class DeletePassword(val password: PasswordUIModel) : Action()
        data class Search(val query: String, val isDeepSearchEnabled: Boolean) : Action()
        data object StopSearching : Action()
        data class ShowHideConfirmDelete(val deletePasswordPair: Pair<Boolean, PasswordUIModel?>) : Action()
        data class ToggleOpenItem(val itemId: String?) : Action()
    }

    data class State(
        val loadingState: LoadingState = LoadingState.Loaded,
        val passwordsList: List<PasswordUIModel> = emptyList(),
        val isSearching: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null)
    )

}