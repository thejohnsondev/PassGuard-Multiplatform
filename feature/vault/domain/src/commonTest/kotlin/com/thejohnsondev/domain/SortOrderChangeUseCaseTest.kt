package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class SortOrderChangeUseCaseTest {

    private val sortOrderChangeUseCase = SortOrderChangeUseCase()

    private val filter1 =
        FiltersProvider.Sorting.sortTitleAZFilterUIModel.copy(id = "1", isSelected = false)
    private val filter2 =
        FiltersProvider.Sorting.sortTitleAZFilterUIModel.copy(id = "2", isSelected = false)
    private val filter3 =
        FiltersProvider.Sorting.sortTitleAZFilterUIModel.copy(id = "3", isSelected = true)

    private val filtersList = listOf(filter1, filter2, filter3)

    @Test
    fun changeSortOrder_selectFilter1() {
        val result = sortOrderChangeUseCase.invoke(filter1, filtersList)
        val expected = listOf(
            filter1.copy(isSelected = true),
            filter2.copy(isSelected = false),
            filter3.copy(isSelected = false)
        )
        assertEquals(expected, result)
    }

    @Test
    fun changeSortOrder_selectFilter2() {
        val result = sortOrderChangeUseCase.invoke(filter2, filtersList)
        val expected = listOf(
            filter1.copy(isSelected = false),
            filter2.copy(isSelected = true),
            filter3.copy(isSelected = false)
        )
        assertEquals(expected, result)
    }

    @Test
    fun changeSortOrder_selectFilter3() {
        val result = sortOrderChangeUseCase.invoke(filter3, filtersList)
        val expected = listOf(
            filter1.copy(isSelected = false),
            filter2.copy(isSelected = false),
            filter3.copy(isSelected = true)
        )
        assertEquals(expected, result)
    }

    @Test
    fun changeSortOrder_noChange() {
        val result = sortOrderChangeUseCase.invoke(filter3, filtersList)
        assertEquals(filtersList, result)
    }
}