package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.Filter
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemTypeFilterChangeUseCaseImplTest {

    private val useCase = ItemTypeFilterChangeUseCaseImpl()

    @Test
    fun whenFilterIsSelectedItShouldBeUnselected() {
        val filters = listOf(
            Filter.testFilter.copy(id = "1", isSelected = true),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[0], false, filters)
        assertEquals(false, result.find { it.id == "1" }?.isSelected)
    }

    @Test
    fun whenFilterIsUnselectedItShouldBeSelected() {
        val filters = listOf(
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[0], true, filters)
        assertEquals(true, result.find { it.id == "1" }?.isSelected)
    }

    @Test
    fun whenOtherFiltersAreUnselectedTheirStateShouldNotChange() {
        val filters = listOf(
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filters[0], true, filters)
        assertEquals(false, result.find { it.id == "2" }?.isSelected)
    }

    @Test
    fun whenOtherFiltersAreSelectedTheirStateShouldNotChange() {
        val filters = listOf(
            Filter.testFilter.copy(id = "1", isSelected = false),
            Filter.testFilter.copy(id = "2", isSelected = true)
        )
        val result = useCase.invoke(filters[0], true, filters)
        assertEquals(true, result.find { it.id == "2" }?.isSelected)
    }

}