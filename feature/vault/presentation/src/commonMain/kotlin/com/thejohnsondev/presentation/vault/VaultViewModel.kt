package com.thejohnsondev.presentation.vault

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.vault.PasswordUIModel

class VaultViewModel : BaseViewModel() {

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
        val currentOpenedItemId: String? = null,
        val isSearching: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null)
    )

}