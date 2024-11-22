package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel
import kotlin.test.Test
import kotlin.test.assertEquals

class SplitItemsListUseCaseImplTest {

    private val useCase = SplitItemsListUseCaseImpl()

    @Test
    fun testReturnsSingleListWhenScreenIsCompact() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false)
        )
        val result = useCase.invoke(true, list)
        assertEquals(1, result.size)
        assertEquals(list, result[0])
    }

    @Test
    fun testSplitsListIntoTwoWhenScreenIsNotCompactAndListHasEvenNumberOfItems() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "3", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "4", isExpanded = false)
        )
        val result = useCase.invoke(false, list)
        assertEquals(2, result.size)
        assertEquals(listOf(list[0], list[2]), result[0])
        assertEquals(listOf(list[1], list[3]), result[1])
    }

    @Test
    fun testSplitsListIntoTwoWhenScreenIsNotCompactAndListHasOddNumberOfItems() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            PasswordUIModel.testPasswordUIModel.copy(id = "3", isExpanded = false)
        )
        val result = useCase.invoke(false, list)
        assertEquals(2, result.size)
        assertEquals(listOf(list[0], list[2]), result[0])
        assertEquals(listOf(list[1]), result[1])
    }

    @Test
    fun testReturnsEmptyListsWhenInputListIsEmpty() {
        val list = emptyList<PasswordUIModel>()
        val result = useCase.invoke(false, list)
        assertEquals(2, result.size)
        assertEquals(emptyList<PasswordUIModel>(), result[0])
        assertEquals(emptyList<PasswordUIModel>(), result[1])
    }
}