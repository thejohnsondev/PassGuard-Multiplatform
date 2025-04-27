package com.thejohnsondev.presentation.vault

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AppliedFiltersService
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.FilterItemsUseCase
import com.thejohnsondev.domain.GetSelectedFiltersIDsUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.ItemFilterChangeUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SortOrderChangeUseCase
import com.thejohnsondev.domain.SortVaultItemsUseCase
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.StopModifiedItemAnimUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.UpdateSelectedFiltersUseCase
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.sync.SyncManager
import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.SortOrder.Companion.toSortOrder
import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class VaultViewModel(
    private val passwordsService: PasswordsService,
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase,
    private val calculateListSizeUseCase: CalculateListSizeUseCase,
    private val splitItemsListUseCase: SplitItemsListUseCase,
    private val searchUseCase: SearchItemsUseCase,
    private val itemFilterChangeUseCase: ItemFilterChangeUseCase,
    private val checkFiltersAppliedUseCase: CheckFiltersAppliedUseCase,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val passwordsMapToUiModelsUseCase: PasswordsMapToUiModelsUseCase,
    private val getSettingsFlowUseCase: GetSettingsFlowUseCase,
    private val filterItemsUseCase: FilterItemsUseCase,
    private val appliedFiltersService: AppliedFiltersService,
    private val updateSelectedFiltersUseCase: UpdateSelectedFiltersUseCase,
    private val getSelectedFiltersIDsUseCase: GetSelectedFiltersIDsUseCase,
    private val sortVaultItemsUseCase: SortVaultItemsUseCase,
    private val sortOrderChangeUseCase: SortOrderChangeUseCase,
    private val stopModifiedItemAnimUseCase: StopModifiedItemAnimUseCase,
    private val copyTextUseCase: CopyTextUseCase,
    private val syncManager: SyncManager
) : BaseViewModel() {

    private val _allPasswordsList = MutableStateFlow<List<PasswordUIModel>>(emptyList())
    private val _settingsConfig = MutableStateFlow<SettingsConfig?>(null)

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
        _settingsConfig
    ) { screenState, state, settings ->
        state.copy(
            screenState = screenState,
            isDeepSearchEnabled = settings?.generalSettings?.isDeepSearchEnabled ?: false
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun perform(action: Action) {
        when (action) {
            is Action.FetchVault -> fetchVault(action.isFromLogin)
            is Action.Search -> search(action.query, action.isDeepSearchEnabled)
            is Action.StopSearching -> stopSearching()
            is Action.ShowHideConfirmDelete -> showHideConfirmDelete(action.deletePasswordPair)
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
            is Action.ToggleIsFiltersOpened -> toggleFiltersOpened()
            is Action.ToggleSortingOpened -> toggleSortingOpened()
            is Action.OnFilterTypeClick -> onFilterTypeClick(
                action.filter,
                action.isSelected
            )

            is Action.OnFilterCategoryClick -> onFilterCategoryClick(
                action.filter,
                action.isSelected
            )

            is Action.OnFilterSortByClick -> onFilterSortByClick(action.filter)
            is Action.OnShowFavoritesAtTopClick -> onShowFavoritesAtTopClick(action.isSelected)

            is Action.OnDeletePasswordClick -> onDeletePasswordClick(action.passwordId)
            is Action.OnAddClick -> onAddClick()
            is Action.OnAddClose -> onAddClose()
            is Action.OnEditClick -> onEditClick(action.password)
            is Action.UpdateIsScreenCompact -> updateIsScreenCompact(action.isCompact)
            is Action.OnMarkAsFavoriteClick -> onMarkAsFavoriteClick(
                action.passwordId,
                action.isFavorite
            )

            is Action.OnCopyClick -> onCopyClick(action.text, false)
            is Action.OnCopySensitiveClick -> onCopyClick(action.text, true)
        }
    }

    private fun onCopyClick(text: String, isSensitive: Boolean) = launch {
        copyTextUseCase(text, isSensitive)
        sendEvent(OneTimeEvent.InfoMessage(DisplayableMessageValue.Copied))
    }

    private fun onShowFavoritesAtTopClick(selected: Boolean) = launch {
        updateShowFavoritesAtTop(selected)
        saveAppliedFilters()
        prepareToUpdateItemsList(_allPasswordsList.value)
    }

    private fun onFilterSortByClick(filter: FilterUIModel) = launch {
        val updatedSortOrderFilters = sortOrderChangeUseCase(
            filter,
            _state.value.sortOrderFilters,
        )
        _state.update {
            it.copy(sortOrderFilters = updatedSortOrderFilters)
        }
        saveAppliedFilters()
        prepareToUpdateItemsList(_allPasswordsList.value)
    }

    private fun onMarkAsFavoriteClick(passwordId: String, isFavorite: Boolean) = launch {
        passwordsService.updateIsFavorite(passwordId, isFavorite)
    }

    private fun updateIsScreenCompact(isCompact: Boolean) = launch {
        _state.update {
            it.copy(isScreenCompact = isCompact)
        }
    }

    private fun onEditClick(password: PasswordUIModel) = launch {
        val updatedPasswordsList = toggleOpenedItemUseCase(password.id, _state.value.passwordsList)
        _state.update {
            it.copy(
                passwordsList = updatedPasswordsList,
                editVaultItemContainer = Pair(true, password)
            )
        }
    }

    private fun onDeletePasswordClick(passwordId: String) = launch {
        passwordsService.deletePassword(passwordId)
    }

    private fun onAddClose() = launch {
        _state.update {
            it.copy(
                editVaultItemContainer = Pair(false, null)
            )
        }
    }

    private fun onAddClick() = launch {
        val updatePasswordsList = toggleOpenedItemUseCase(null, _state.value.passwordsList)
        _state.update {
            it.copy(
                passwordsList = updatePasswordsList,
                editVaultItemContainer = Pair(true, null)
            )
        }
    }

    private fun fetchVault(isFromLogin: Boolean) = launchLoading {
        callSync(isFromLogin)
        fetchFilters()
        fetchSettings()
        fetchPasswords()
    }

    private fun callSync(isFromLogin: Boolean) {
        if (isFromLogin) {
            syncManager.syncOnLogin()
        } else {
            syncManager.syncOnAppLaunch()
        }
    }

    private suspend fun fetchFilters() {
        val itemTypeFilters = FiltersProvider.ItemType.getVaultItemTypeFilters()
        val itemCategoryFilters = FiltersProvider.Category.getVaultCategoryFilters()
        val sortOrderFilters = FiltersProvider.Sorting.getSortOrderFilters()

        val isFiltersOpened = appliedFiltersService.getIsOpenedFilters()

        val appliedItemTypeFiltersIDs = appliedFiltersService.getAppliedItemTypeFilters()
        val appliedCategoryFiltersIDs = appliedFiltersService.getAppliedItemCategoryFilters()
        val appliedSortOrderID = appliedFiltersService.getAppliedSortOrder()
        val showFavoritesAtTop = appliedFiltersService.getAppliedShowFavoritesAtTop()

        val updatedItemTypeFilters =
            updateSelectedFiltersUseCase(itemTypeFilters, appliedItemTypeFiltersIDs)
        val updatedCategoryFilters =
            updateSelectedFiltersUseCase(itemCategoryFilters, appliedCategoryFiltersIDs)
        val updatedSortOrderFilters = updateSelectedFiltersUseCase(
            sortOrderFilters,
            listOf(appliedSortOrderID)
        )
        val isAnyFiltersApplied =
            checkFiltersAppliedUseCase(updatedItemTypeFilters, updatedCategoryFilters)
        _state.update {
            it.copy(
                itemTypeFilters = updatedItemTypeFilters,
                itemCategoryFilters = updatedCategoryFilters,
                isAnyFiltersApplied = isAnyFiltersApplied,
                sortOrderFilters = updatedSortOrderFilters,
                isFiltersOpened = isFiltersOpened,
            )
        }
        updateShowFavoritesAtTop(showFavoritesAtTop)
    }

    private fun updateShowFavoritesAtTop(isSelected: Boolean) {
        _state.update {
            it.copy(
                showFavoritesAtTopFilter = _state.value.showFavoritesAtTopFilter.copy(isSelected = isSelected)
            )
        }
    }

    private fun fetchSettings() = launch {
        getSettingsFlowUseCase.invoke().collect {
            _settingsConfig.emit(it)
        }
    }

    private suspend fun prepareToUpdateItemsList(items: List<PasswordUIModel>) {
        val filteredItems = filterItemsUseCase(
            items = items,
            typeFilters = _state.value.itemTypeFilters,
            categoryFilters = _state.value.itemCategoryFilters,
        )
        val sortedList = sortVaultItemsUseCase(
            filteredItems,
            sortOrder = _state.value.sortOrderFilters.toSortOrder(),
            showFavoritesAtTop = _state.value.showFavoritesAtTopFilter.isSelected
        )
        val dividedItems = splitItemsListUseCase(_state.value.isScreenCompact, sortedList)
        val itemsHeight = calculateListSizeUseCase(dividedItems)
        _state.update {
            it.copy(
                listHeight = itemsHeight,
                passwordsList = dividedItems
            )
        }
    }

    private suspend fun fetchPasswords()  {
        passwordsService.getUserPasswords().collect { items ->
            val decryptedPasswordDtoList = decryptPasswordsListUseCase(items)
            val passwordsUiModels = passwordsMapToUiModelsUseCase(decryptedPasswordDtoList)
            prepareToUpdateItemsList(passwordsUiModels)
            _allPasswordsList.emit(passwordsUiModels)
            _state.update {
                it.copy(
                    isVaultEmpty = items.isEmpty()
                )
            }
            showContent()
            stopModifiedItemAnim()
        }
    }

    private fun stopModifiedItemAnim() = launch {
        val updatedList = stopModifiedItemAnimUseCase(_allPasswordsList.value)
        _allPasswordsList.emit(updatedList)
        prepareToUpdateItemsList(updatedList)
    }

    private fun onFilterTypeClick(filter: FilterUIModel, isSelected: Boolean) = launch {
        val updatedTypeFilters =
            itemFilterChangeUseCase(filter, isSelected, _state.value.itemTypeFilters)
        val isAnyFiltersApplied =
            checkFiltersAppliedUseCase(updatedTypeFilters, _state.value.itemCategoryFilters)
        _state.update {
            it.copy(
                itemTypeFilters = updatedTypeFilters,
                isAnyFiltersApplied = isAnyFiltersApplied
            )
        }
        saveAppliedFilters()
        prepareToUpdateItemsList(_allPasswordsList.value)
    }

    private fun onFilterCategoryClick(
        filter: FilterUIModel,
        isSelected: Boolean,
    ) = launch {
        val updatedCategoryFilters =
            itemFilterChangeUseCase(filter, isSelected, _state.value.itemCategoryFilters)
        val isAnyFiltersApplied =
            checkFiltersAppliedUseCase(_state.value.itemTypeFilters, updatedCategoryFilters)
        _state.update {
            it.copy(
                itemCategoryFilters = updatedCategoryFilters,
                isAnyFiltersApplied = isAnyFiltersApplied,
            )
        }
        saveAppliedFilters()
        prepareToUpdateItemsList(_allPasswordsList.value)
    }

    private fun saveAppliedFilters() = launch {
        val appliedItemTypeFilters = getSelectedFiltersIDsUseCase(_state.value.itemTypeFilters)
        val appliedCategoryFilters = getSelectedFiltersIDsUseCase(_state.value.itemCategoryFilters)
        val appliedSortOrder = getSelectedFiltersIDsUseCase(_state.value.sortOrderFilters)
        val showFavoritesAtTop = _state.value.showFavoritesAtTopFilter.isSelected
        appliedFiltersService.updateAppliedItemTypeFilters(appliedItemTypeFilters)
        appliedFiltersService.updateAppliedCategoryFilters(appliedCategoryFilters)
        appliedSortOrder.firstOrNull()?.let {
            appliedFiltersService.updateAppliedSortOrder(it)
        }
        appliedFiltersService.updateAppliedShowFavoritesAtTop(showFavoritesAtTop)
    }

    private fun toggleFiltersOpened() = launch {
        val newIsFiltersOpenedValue = !_state.value.isFiltersOpened
        _state.update {
            it.copy(isFiltersOpened = newIsFiltersOpenedValue)
        }
        appliedFiltersService.updateOpenedFilters(newIsFiltersOpenedValue)
    }

    private fun toggleSortingOpened() {
        _state.update { it.copy(isSortingOpened = !it.isSortingOpened) }
    }

    private fun search(query: String, isDeepSearchEnabled: Boolean) = launch {
        if (query.isEmpty()) {
            stopSearching()
            return@launch
        }
        val resultList = searchUseCase(query, isDeepSearchEnabled, _allPasswordsList.value)
        prepareToUpdateItemsList(resultList)
        _state.update {
            it.copy(
                isSearching = true
            )
        }
    }

    private fun stopSearching() = launch {
        prepareToUpdateItemsList(_allPasswordsList.value)
        _state.update {
            it.copy(
                isSearching = false,
            )
        }
    }

    private fun showHideConfirmDelete(deletePasswordPair: Pair<Boolean, PasswordUIModel?>) {
        // TODO implement
    }

    private fun toggleOpenItem(newOpenedItemId: String?) = launch {
        val updatedList = toggleOpenedItemUseCase(
            newOpenedItemId,
            _state.value.passwordsList
        )
        val itemsHeight = calculateListSizeUseCase(updatedList)
        _state.update {
            it.copy(
                passwordsList = updatedList,
                listHeight = itemsHeight
            )
        }
    }


    sealed class Action {
        data class UpdateIsScreenCompact(val isCompact: Boolean) : Action()
        data class FetchVault(
            val isFromLogin: Boolean = false
        ) : Action()
        data class Search(
            val query: String,
            val isDeepSearchEnabled: Boolean,
        ) : Action()

        data object StopSearching : Action()
        data class ShowHideConfirmDelete(
            val deletePasswordPair: Pair<Boolean, PasswordUIModel?>,
        ) : Action()

        data class ToggleOpenItem(val itemId: String?) : Action()
        data object ToggleIsFiltersOpened : Action()
        data object ToggleSortingOpened : Action()
        data class OnFilterTypeClick(
            val filter: FilterUIModel,
            val isSelected: Boolean,
        ) : Action()

        data class OnFilterCategoryClick(
            val filter: FilterUIModel,
            val isSelected: Boolean,
        ) : Action()

        data class OnFilterSortByClick(
            val filter: FilterUIModel,
        ) : Action()

        data class OnShowFavoritesAtTopClick(
            val isSelected: Boolean,
        ) : Action()

        data object OnAddClick : Action()
        data object OnAddClose : Action()
        data class OnEditClick(
            val password: PasswordUIModel,
        ) : Action()

        data class OnDeletePasswordClick(val passwordId: String) : Action()
        data class OnMarkAsFavoriteClick(val passwordId: String, val isFavorite: Boolean) : Action()

        data class OnCopyClick(val text: String): Action()
        data class OnCopySensitiveClick(val text: String): Action()
    }

    data class State(
        val isScreenCompact: Boolean = false,
        val screenState: ScreenState = ScreenState.None,
        val passwordsList: List<List<PasswordUIModel>> = listOf(emptyList()),
        val isSearching: Boolean = false,
        val isFiltersOpened: Boolean = false,
        val isSortingOpened: Boolean = false,
        val isAnyFiltersApplied: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
        val listHeight: Int = 0,
        val itemTypeFilters: List<FilterUIModel> = listOf(),
        val itemCategoryFilters: List<FilterUIModel> = listOf(),
        val sortOrderFilters: List<FilterUIModel> = listOf(),
        val showFavoritesAtTopFilter: FilterUIModel = FiltersProvider.Sorting.sortShowFavoritesAtTopFilterUIModel,
        val isVaultEmpty: Boolean = false,
        val editVaultItemContainer: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
    )

}