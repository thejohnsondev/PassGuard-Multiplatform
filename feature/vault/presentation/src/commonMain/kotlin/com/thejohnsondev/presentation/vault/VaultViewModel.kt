package com.thejohnsondev.presentation.vault

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.domain.models.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class VaultViewModel(
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase,
    private val calculateListSizeUseCase: CalculateListSizeUseCase,
    private val splitItemsListUseCase: SplitItemsListUseCase,
    private val searchUseCase: SearchItemsUseCase
) : BaseViewModel() {

    private val _allPasswordsList = MutableStateFlow<List<PasswordUIModel>>(emptyList())
    private val _passwordsList = MutableStateFlow<List<List<PasswordUIModel>>>(emptyList())
    private val _isSearching = MutableStateFlow(false)
    private val _isDeepSearchingEnabled = MutableStateFlow(false)
    private val _showHideConfirmDelete =
        MutableStateFlow<Pair<Boolean, PasswordUIModel?>>(Pair(false, null))
    private val _listHeight = MutableStateFlow(0)

    val state = combine(
        _loadingState,
        _passwordsList,
        _isSearching,
        _isDeepSearchingEnabled, // TODO replace with setting from settings config
        _showHideConfirmDelete,
        _listHeight,
        ::State
    )

    fun perform(action: Action) {
        when (action) {
            is Action.FetchVault -> fetchVault(action.isCompact)
            is Action.DeletePassword -> deletePassword(action.password)
            is Action.Search -> search(action.isCompact, action.query, action.isDeepSearchEnabled)
            is Action.StopSearching -> stopSearching(action.isCompact)
            is Action.ShowHideConfirmDelete -> showHideConfirmDelete(action.deletePasswordPair)
            is Action.ToggleOpenItem -> toggleOpenItem(action.isCompact, action.itemId)
        }
    }

    private fun fetchVault(isCompact: Boolean) = launch {
        val items = PasswordUIModel.testPasswordItems
        _allPasswordsList.emit(items)
        val dividedItems = splitItemsListUseCase(isCompact, items)
        val itemsHeight = calculateListSizeUseCase(dividedItems)
        _listHeight.emit(itemsHeight)
        _passwordsList.emit(dividedItems)
    }

    private fun deletePassword(password: PasswordUIModel) {
        // TODO implement
    }

    private fun search(isCompact: Boolean, query: String, isDeepSearchEnabled: Boolean) = launch {
        _isSearching.emit(true)
        val resultList = searchUseCase(query, isDeepSearchEnabled, _allPasswordsList.value)
        val dividedItems = splitItemsListUseCase(isCompact, resultList)
        val itemsHeight = calculateListSizeUseCase(dividedItems)
        _listHeight.emit(itemsHeight)
        _passwordsList.emit(dividedItems)
    }

    private fun stopSearching(isCompact: Boolean) = launch {
        _isSearching.emit(false)
        val dividedItems = splitItemsListUseCase(isCompact, _allPasswordsList.value)
        val itemsHeight = calculateListSizeUseCase(dividedItems)
        _listHeight.emit(itemsHeight)
        _passwordsList.emit(dividedItems)
    }

    private fun showHideConfirmDelete(deletePasswordPair: Pair<Boolean, PasswordUIModel?>) {
        // TODO implement
    }

    private fun toggleOpenItem(isCompact: Boolean, newOpenedItemId: String?) = launch {
        val updatedList = toggleOpenedItemUseCase(
            newOpenedItemId,
            _passwordsList.value
        )
        val itemsHeight = calculateListSizeUseCase(updatedList)
        _listHeight.emit(itemsHeight)
        _passwordsList.emit(updatedList)
    }


    sealed class Action {
        data class FetchVault(val isCompact: Boolean) : Action()
        data class DeletePassword(val password: PasswordUIModel) : Action()
        data class Search(val isCompact: Boolean, val query: String, val isDeepSearchEnabled: Boolean) : Action()
        data class StopSearching(val isCompact: Boolean) : Action()
        data class ShowHideConfirmDelete(val deletePasswordPair: Pair<Boolean, PasswordUIModel?>) : Action()
        data class ToggleOpenItem(val isCompact: Boolean, val itemId: String?) : Action()
    }

    data class State(
        val loadingState: LoadingState = LoadingState.Loaded,
        val passwordsList: List<List<PasswordUIModel>> = listOf(emptyList()),
        val isSearching: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
        val listHeight: Int = 0
    )

}