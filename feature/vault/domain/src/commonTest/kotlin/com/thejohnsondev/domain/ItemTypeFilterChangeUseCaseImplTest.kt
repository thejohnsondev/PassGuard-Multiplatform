package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.FilterUIModel
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemTypeFilterChangeUseCaseImplTest {

    private val useCase = ItemTypeFilterChangeUseCaseImpl()

    @Test
    fun whenFilterIsSelectedItShouldBeUnselected() {
        val filterUIModels = listOf(
            FilterUIModel.testFilterUIModel.copy(id = "1", isSelected = true),
            FilterUIModel.testFilterUIModel.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filterUIModels[0], false, filterUIModels)
        assertEquals(false, result.find { it.id == "1" }?.isSelected)
    }

    @Test
    fun whenFilterIsUnselectedItShouldBeSelected() {
        val filterUIModels = listOf(
            FilterUIModel.testFilterUIModel.copy(id = "1", isSelected = false),
            FilterUIModel.testFilterUIModel.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filterUIModels[0], true, filterUIModels)
        assertEquals(true, result.find { it.id == "1" }?.isSelected)
    }

    @Test
    fun whenOtherFiltersAreUnselectedTheirStateShouldNotChange() {
        val filterUIModels = listOf(
            FilterUIModel.testFilterUIModel.copy(id = "1", isSelected = false),
            FilterUIModel.testFilterUIModel.copy(id = "2", isSelected = false)
        )
        val result = useCase.invoke(filterUIModels[0], true, filterUIModels)
        assertEquals(false, result.find { it.id == "2" }?.isSelected)
    }

    @Test
    fun whenOtherFiltersAreSelectedTheirStateShouldNotChange() {
        val filterUIModels = listOf(
            FilterUIModel.testFilterUIModel.copy(id = "1", isSelected = false),
            FilterUIModel.testFilterUIModel.copy(id = "2", isSelected = true)
        )
        val result = useCase.invoke(filterUIModels[0], true, filterUIModels)
        assertEquals(true, result.find { it.id == "2" }?.isSelected)
    }

}