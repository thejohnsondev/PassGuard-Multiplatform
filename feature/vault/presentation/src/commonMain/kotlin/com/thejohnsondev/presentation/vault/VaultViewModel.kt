package com.thejohnsondev.presentation.vault

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.uimodel.filterlists.getVaultCategoryFilters
import com.thejohnsondev.uimodel.filterlists.getVaultItemTypeFilters
import com.thejohnsondev.uimodel.models.FilterUIModel
import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow

class VaultViewModel(
    private val passwordsService: PasswordsService,
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase,
    private val calculateListSizeUseCase: CalculateListSizeUseCase,
    private val splitItemsListUseCase: SplitItemsListUseCase,
    private val searchUseCase: SearchItemsUseCase,
    private val itemTypeFilterChangeUseCase: ItemTypeFilterChangeUseCase,
    private val checkFiltersAppliedUseCase: CheckFiltersAppliedUseCase,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val passwordsMapToUiModelsUseCase: PasswordsMapToUiModelsUseCase
) : BaseViewModel() {

    private val _allPasswordsList = MutableStateFlow<List<PasswordUIModel>>(emptyList())
    private val _passwordsList = MutableStateFlow<List<List<PasswordUIModel>>>(emptyList())
    private val _isSearching = MutableStateFlow(false)
    private val _isFiltersOpened = MutableStateFlow(false)
    private val _isAnyFiltersApplied = MutableStateFlow(false)
    private val _isDeepSearchingEnabled = MutableStateFlow(true)
    private val _showHideConfirmDelete =
        MutableStateFlow<Pair<Boolean, PasswordUIModel?>>(Pair(false, null))
    private val _listHeight = MutableStateFlow(0)
    private val _itemTypeFilters = MutableStateFlow(getVaultItemTypeFilters())
    private val _itemCategoryFilters = MutableStateFlow(getVaultCategoryFilters())
    private val _isVaultEmpty = MutableStateFlow(false)
    private val _editVaultItemContainer =
        MutableStateFlow<Pair<Boolean, PasswordUIModel?>>(Pair(false, null))

    val state = combine(
        _screenState,
        _passwordsList,
        _isSearching,
        _isFiltersOpened,
        _isAnyFiltersApplied,
        _isDeepSearchingEnabled, // TODO replace with setting from settings config
        _showHideConfirmDelete,
        _listHeight,
        _itemTypeFilters,
        _itemCategoryFilters,
        _isVaultEmpty,
        _editVaultItemContainer,
        ::State
    )

    fun perform(action: Action) {
        when (action) {
            is Action.FetchVault -> fetchVault(action.isCompact)
            is Action.DeletePassword -> deletePassword(action.password)
            is Action.Search -> search(action.isCompact, action.query, action.isDeepSearchEnabled)
            is Action.StopSearching -> stopSearching(action.isCompact)
            is Action.ShowHideConfirmDelete -> showHideConfirmDelete(action.deletePasswordPair)
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
            is Action.ToggleIsFiltersOpened -> toggleFiltersOpened()
            is Action.OnFilterTypeClick -> onFilterTypeClick(action.filter, action.isSelected)
            is Action.OnFilterCategoryClick -> onFilterCategoryClick(action.filter, action.isSelected)
            is Action.OnDeletePasswordClick -> onDeletePasswordClick(action.passwordId)
            is Action.OnAddClick -> onAddClick()
            is Action.OnAddClose -> onAddClose()
            is Action.OnEditClick -> onEditClick(action.password)
        }
    }

    private fun onEditClick(password: PasswordUIModel) = launch {
        _editVaultItemContainer.emit(Pair(true, password))
    }

    private fun onDeletePasswordClick(passwordId: String) = launch {
        passwordsService.deletePassword(passwordId)
    }

    private fun onAddClose() = launch {
        _editVaultItemContainer.emit(Pair(false, null))
    }

    private fun onAddClick() = launch {
        _editVaultItemContainer.emit(Pair(true, null))
    }

    private fun fetchVault(isCompact: Boolean) = launchLoading {
        passwordsService.getUserPasswords().collect { items ->
            val decryptedPasswordDtoList = decryptPasswordsListUseCase(items)
            val passwordsUiModels = passwordsMapToUiModelsUseCase(decryptedPasswordDtoList)
            _allPasswordsList.emit(passwordsUiModels)
            _isVaultEmpty.emit(items.isEmpty())
            val dividedItems = splitItemsListUseCase(isCompact, passwordsUiModels)
            val itemsHeight = calculateListSizeUseCase(dividedItems)
            _listHeight.emit(itemsHeight)
            _passwordsList.emit(dividedItems)
            showContent()
        }
    }

    private fun onFilterTypeClick(filter: FilterUIModel, isSelected: Boolean) = launch {
        val updatedTypeFilters = itemTypeFilterChangeUseCase(filter, isSelected, _itemTypeFilters.value)
        // todo filter list
        _itemTypeFilters.emit(updatedTypeFilters)
        val isAnyFiltersApplied = checkFiltersAppliedUseCase(updatedTypeFilters, _itemCategoryFilters.value)
        _isAnyFiltersApplied.emit(isAnyFiltersApplied)
    }

    private fun onFilterCategoryClick(filter: FilterUIModel, isSelected: Boolean) = launch {
        val updatedCategoryFilters = itemTypeFilterChangeUseCase(filter, isSelected, _itemCategoryFilters.value)
        // todo filter list
        _itemCategoryFilters.emit(updatedCategoryFilters)
        val isAnyFiltersApplied = checkFiltersAppliedUseCase(_itemTypeFilters.value, updatedCategoryFilters)
        _isAnyFiltersApplied.emit(isAnyFiltersApplied)
    }

    private fun toggleFiltersOpened() {
        _isFiltersOpened.value = !_isFiltersOpened.value
    }

    private fun deletePassword(password: PasswordUIModel) {
        // TODO implement
    }

    private fun search(isCompact: Boolean, query: String, isDeepSearchEnabled: Boolean) = launch {
        if (query.isEmpty()) {
            stopSearching(isCompact)
            return@launch
        }
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

    private fun toggleOpenItem(newOpenedItemId: String?) = launch {
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
        data class ToggleOpenItem(val itemId: String?) : Action()
        data object ToggleIsFiltersOpened : Action()
        data class OnFilterTypeClick(val filter: FilterUIModel, val isSelected: Boolean) : Action()
        data class OnFilterCategoryClick(val filter: FilterUIModel, val isSelected: Boolean) : Action()
        data object OnAddClick : Action()
        data object OnAddClose : Action()
        data class OnEditClick(
            val password: PasswordUIModel
        ) : Action()
        data class OnDeletePasswordClick(val passwordId: String) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val passwordsList: List<List<PasswordUIModel>> = listOf(emptyList()),
        val isSearching: Boolean = false,
        val isFiltersOpened: Boolean = false,
        val isAnyFiltersApplied: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
        val listHeight: Int = 0,
        val itemTypeFilters: List<FilterUIModel> = emptyList(),
        val itemCategoryFilters: List<FilterUIModel> = emptyList(),
        val isVaultEmpty: Boolean = false,
        val editVaultItemContainer: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
    )

}