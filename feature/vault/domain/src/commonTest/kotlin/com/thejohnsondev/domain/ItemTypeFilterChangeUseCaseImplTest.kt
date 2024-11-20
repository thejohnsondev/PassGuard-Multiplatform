package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.Filter
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemTypeFilterChangeUseCaseImplTest {

    private val useCase = ItemTypeFilterChangeUseCaseImpl()

    @Test
    fun whenAllFilterIsSelectedAllFiltersShouldBeSelected() {
        val filters = listOf(
            Filter.testFilter.copy(id = Filter.FILTER_ALL, isSelected = false),
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[0], true, filters)
        assertEquals(true, result.all { it.isSelected })
    }

    @Test
    fun whenAllFilterIsUnselectedNoChangesShouldBeMade() {
        val filters = listOf(
            Filter.testFilter.copy(id = Filter.FILTER_ALL, isSelected = true),
            Filter.testFilter.copy(id = "1", isSelected = true),
            Filter.testFilter.copy(id = "2", isSelected = true)
        )
        val result = useCase.invoke(filters[0], false, filters)
        assertEquals(filters, result)
    }

    @Test
    fun whenASingleNonAllFilterIsSelectedAllFilterShouldBeUnselected() {
        val filters = listOf(
            Filter.testFilter.copy(id = Filter.FILTER_ALL, isSelected = true),
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[1], true, filters)
        assertEquals(false, result.find { it.id == Filter.FILTER_ALL }?.isSelected)
        assertEquals(true, result.find { it.id == "1" }?.isSelected)
    }

    @Test
    fun whenASingleNonAllFilterIsUnselectedAndItIsTheOnlySelectedFilterNoChangesShouldBeMade() {
        val filters = listOf(
            Filter.testFilter.copy(id = Filter.FILTER_ALL, isSelected = false),
            Filter.testFilter.copy(id = "1", isSelected = true),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[1], false, filters)
        assertEquals(filters, result)
    }

    @Test
    fun whenNoFiltersAreSelectedAllFiltersShouldBeSelected() {
        val filters = listOf(
            Filter.testFilter.copy(id = Filter.FILTER_ALL, isSelected = false),
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[1], false, filters)
        assertEquals(true, result.all { it.isSelected })
    }

}