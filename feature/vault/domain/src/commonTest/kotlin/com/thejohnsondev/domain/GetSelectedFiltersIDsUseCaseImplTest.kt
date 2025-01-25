package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSelectedFiltersIDsUseCaseImplTest {

    private val getSelectedFiltersIDsUseCase = GetSelectedFiltersIDsUseCaseImpl()

    @Test
    fun invoke_noFilters_returnsEmptyList() {
        val filters = emptyList<FilterUIModel>()

        val result = runBlocking {
            getSelectedFiltersIDsUseCase.invoke(filters)
        }

        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun invoke_noSelectedFilters_returnsEmptyList() {
        val filters = listOf(
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(isSelected = false),
            FiltersProvider.ItemType.notesFilterUIModel.copy(isSelected = false)
        )

        val result = runBlocking {
            getSelectedFiltersIDsUseCase.invoke(filters)
        }

        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun invoke_allFiltersSelected_returnsAllIDs() {
        val filters = listOf(
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "1", isSelected = true),
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "2", isSelected = true)
        )

        val result = runBlocking {
            getSelectedFiltersIDsUseCase.invoke(filters)
        }

        val expected = listOf("1", "2")
        assertEquals(expected, result)
    }

    @Test
    fun invoke_someFiltersSelected_returnsSelectedIDs() {
        val filters = listOf(
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "1", isSelected = true),
            FiltersProvider.ItemType.notesFilterUIModel.copy(id = "2", isSelected = false),
            FiltersProvider.ItemType.bankAccountsFilterUIModel.copy(id = "3", isSelected = true)
        )

        val result = runBlocking {
            getSelectedFiltersIDsUseCase.invoke(filters)
        }

        val expected = listOf("1", "3")
        assertEquals(expected, result)
    }

    @Test
    fun invoke_noMatchingSelectedFilters_returnsEmptyList() {
        val filters = listOf(
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "1", isSelected = false),
            FiltersProvider.ItemType.passwordsFilterUIModel.copy(id = "2", isSelected = false)
        )

        val result = runBlocking {
            getSelectedFiltersIDsUseCase.invoke(filters)
        }

        assertEquals(emptyList<String>(), result)
    }

}