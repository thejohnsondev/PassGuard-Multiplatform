package com.thejohnsondev.presentation.vault

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.FilterItemsUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.getVaultCategoryFilters
import com.thejohnsondev.ui.model.filterlists.getVaultItemTypeFilters
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
    private val itemTypeFilterChangeUseCase: ItemTypeFilterChangeUseCase,
    private val checkFiltersAppliedUseCase: CheckFiltersAppliedUseCase,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val passwordsMapToUiModelsUseCase: PasswordsMapToUiModelsUseCase,
    private val getSettingsFlowUseCase: GetSettingsFlowUseCase,
    private val filterItemsUseCase: FilterItemsUseCase
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
            is Action.FetchVault -> fetchVault()
            is Action.Search -> search(action.query, action.isDeepSearchEnabled)
            is Action.StopSearching -> stopSearching()
            is Action.ShowHideConfirmDelete -> showHideConfirmDelete(action.deletePasswordPair)
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
            is Action.ToggleIsFiltersOpened -> toggleFiltersOpened()
            is Action.OnFilterTypeClick -> onFilterTypeClick(
                action.filter,
                action.isSelected
            )

            is Action.OnFilterCategoryClick -> onFilterCategoryClick(
                action.filter,
                action.isSelected
            )

            is Action.OnDeletePasswordClick -> onDeletePasswordClick(action.passwordId)
            is Action.OnAddClick -> onAddClick()
            is Action.OnAddClose -> onAddClose()
            is Action.OnEditClick -> onEditClick(action.password)
            is Action.UpdateIsScreenCompact -> updateIsScreenCompact(action.isCompact)
        }
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

    private fun fetchVault() {
        fetchPasswords()
        fetchSettings()
    }

    private fun fetchSettings() = launch {
        getSettingsFlowUseCase.invoke().collect {
            _settingsConfig.emit(it)
        }
    }

    private fun prepareToUpdateItemsList(items: List<PasswordUIModel>) {
        val dividedItems = splitItemsListUseCase(_state.value.isScreenCompact, items)
        val itemsHeight = calculateListSizeUseCase(dividedItems)
        _state.update {
            it.copy(
                listHeight = itemsHeight,
                passwordsList = dividedItems
            )
        }
    }

    private fun fetchPasswords() = launchLoading {
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
        }
    }

    private fun onFilterTypeClick(filter: FilterUIModel, isSelected: Boolean) = launch {
        val updatedTypeFilters =
            itemTypeFilterChangeUseCase(filter, isSelected, _state.value.itemTypeFilters)
        val isAnyFiltersApplied =
            checkFiltersAppliedUseCase(updatedTypeFilters, _state.value.itemCategoryFilters)
        val filteredItems = filterItemsUseCase(
            _allPasswordsList.value,
            updatedTypeFilters,
            _state.value.itemCategoryFilters
        )
        prepareToUpdateItemsList(filteredItems)
        _state.update {
            it.copy(
                itemTypeFilters = updatedTypeFilters,
                isAnyFiltersApplied = isAnyFiltersApplied
            )
        }
    }

    private fun onFilterCategoryClick(
        filter: FilterUIModel,
        isSelected: Boolean
    ) = launch {
        val updatedCategoryFilters =
            itemTypeFilterChangeUseCase(filter, isSelected, _state.value.itemCategoryFilters)
        val isAnyFiltersApplied =
            checkFiltersAppliedUseCase(_state.value.itemTypeFilters, updatedCategoryFilters)
        val filteredItems = filterItemsUseCase(
            _allPasswordsList.value,
            _state.value.itemTypeFilters,
            updatedCategoryFilters
        )
        prepareToUpdateItemsList(filteredItems)
        _state.update {
            it.copy(
                itemCategoryFilters = updatedCategoryFilters,
                isAnyFiltersApplied = isAnyFiltersApplied,
            )
        }
    }

    private fun toggleFiltersOpened() {
        _state.update {
            it.copy(isFiltersOpened = !it.isFiltersOpened)
        }
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
        data object FetchVault : Action()
        data class Search(
            val query: String,
            val isDeepSearchEnabled: Boolean
        ) : Action()

        data object StopSearching : Action()
        data class ShowHideConfirmDelete(
            val deletePasswordPair: Pair<Boolean, PasswordUIModel?>
        ) : Action()

        data class ToggleOpenItem(val itemId: String?) : Action()
        data object ToggleIsFiltersOpened : Action()
        data class OnFilterTypeClick(
            val filter: FilterUIModel,
            val isSelected: Boolean
        ) : Action()

        data class OnFilterCategoryClick(
            val filter: FilterUIModel,
            val isSelected: Boolean
        ) : Action()

        data object OnAddClick : Action()
        data object OnAddClose : Action()
        data class OnEditClick(
            val password: PasswordUIModel
        ) : Action()

        data class OnDeletePasswordClick(val passwordId: String) : Action()
    }

    data class State(
        val isScreenCompact: Boolean = false,
        val screenState: ScreenState = ScreenState.None,
        val passwordsList: List<List<PasswordUIModel>> = listOf(emptyList()),
        val isSearching: Boolean = false,
        val isFiltersOpened: Boolean = false,
        val isAnyFiltersApplied: Boolean = false,
        val isDeepSearchEnabled: Boolean = false,
        val deletePasswordPair: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
        val listHeight: Int = 0,
        val itemTypeFilters: List<FilterUIModel> = getVaultItemTypeFilters(),
        val itemCategoryFilters: List<FilterUIModel> = getVaultCategoryFilters(),
        val isVaultEmpty: Boolean = false,
        val editVaultItemContainer: Pair<Boolean, PasswordUIModel?> = Pair(false, null),
    )

}