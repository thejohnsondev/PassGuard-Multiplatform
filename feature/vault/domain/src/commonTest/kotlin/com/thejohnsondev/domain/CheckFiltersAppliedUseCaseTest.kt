package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckFiltersAppliedUseCaseTest {

    private val useCase = CheckFiltersAppliedUseCase()

    @Test
    fun testNoFiltersApplied() {
        val typeFiltersList = listOf<FilterUIModel>()
        val categoryFiltersList = listOf<FilterUIModel>()
        val result = useCase.invoke(typeFiltersList, categoryFiltersList)
        assertFalse(result)
    }

    @Test
    fun testTypeFilterApplied() {
        val typeFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = true))
        val categoryFiltersList = listOf<FilterUIModel>()
        val result = useCase.invoke(typeFiltersList, categoryFiltersList)
        assertTrue(result)
    }

    @Test
    fun testCategoryFilterApplied() {
        val typeFiltersList = listOf<FilterUIModel>()
        val categoryFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = true))
        val result = useCase.invoke(typeFiltersList, categoryFiltersList)
        assertTrue(result)
    }

    @Test
    fun testBothFiltersApplied() {
        val typeFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = true))
        val categoryFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = true))
        val result = useCase.invoke(typeFiltersList, categoryFiltersList)
        assertTrue(result)
    }

    @Test
    fun testNoFiltersSelected() {
        val typeFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = false))
        val categoryFiltersList = listOf(FilterUIModel.testFilterUIModel.copy(isSelected = false))
        val result = useCase.invoke(typeFiltersList, categoryFiltersList)
        assertFalse(result)
    }

}