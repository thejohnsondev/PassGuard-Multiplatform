package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateSelectedFiltersUseCaseImplTest {

    private val updateSelectedFiltersUseCase = UpdateSelectedFiltersUseCaseImpl()

    @Test
    fun invoke_noFilters_returnsEmptyList() {
        val filters = emptyList<FilterUIModel>()
        val appliedFiltersIDs = emptyList<String>()

        val result = runBlocking {
            updateSelectedFiltersUseCase.invoke(filters, appliedFiltersIDs)
        }

        assertEquals(emptyList<FilterUIModel>(), result)
    }

    @Test
    fun invoke_noAppliedFilters_returnsAllUnselected() {
        val filters = listOf(
            FiltersProvider.ItemType.notesFilterUIModel.copy(isSelected = false),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(isSelected = false)
        )
        val appliedFiltersIDs = emptyList<String>()

        val result = runBlocking {
            updateSelectedFiltersUseCase.invoke(filters, appliedFiltersIDs)
        }

        assertEquals(filters, result)
    }

    @Test
    fun invoke_allFiltersApplied_returnsAllSelected() {
        val filters = listOf(
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "1", isSelected = false),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "2", isSelected = false)
        )
        val appliedFiltersIDs = listOf("1", "2")

        val result = runBlocking {
            updateSelectedFiltersUseCase.invoke(filters, appliedFiltersIDs)
        }

        val expected = filters.map { it.copy(isSelected = true) }
        assertEquals(expected, result)
    }

    @Test
    fun invoke_someFiltersApplied_returnsMixedSelection() {
        val filters = listOf(
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "1", isSelected = false),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "2", isSelected = false),
            FiltersProvider.ItemType.bankAccountsFilterUIModel.copy(id = "3", isSelected = false),
        )
        val appliedFiltersIDs = listOf("1", "3")

        val result = runBlocking {
            updateSelectedFiltersUseCase.invoke(filters, appliedFiltersIDs)
        }

        val expected = listOf(
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "1", isSelected = true),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "2", isSelected = false),
            FiltersProvider.ItemType.bankAccountsFilterUIModel.copy(id = "3", isSelected = true),
        )
        assertEquals(expected, result)
    }

    @Test
    fun invoke_noMatchingFilters_returnsAllUnselected() {
        val filters = listOf(
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "1", isSelected = false),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "2", isSelected = false)
        )
        val appliedFiltersIDs = listOf("3", "4")

        val result = runBlocking {
            updateSelectedFiltersUseCase.invoke(filters, appliedFiltersIDs)
        }

        assertEquals(filters, result)
    }

}