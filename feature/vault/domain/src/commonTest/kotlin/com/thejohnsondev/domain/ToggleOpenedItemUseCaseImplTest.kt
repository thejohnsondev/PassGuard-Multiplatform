package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ToggleOpenedItemUseCaseImplTest {

    private val useCase = ToggleOpenedItemUseCaseImpl()

    @Test
    fun itemWithMatchingIdIsExpanded() = runTest {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke("1", list)
        assertEquals(true, result[0][0].isExpanded)
        assertEquals(false, result[0][1].isExpanded)
    }

    @Test
    fun itemWithMatchingIdIsCollapsed() = runBlocking {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = true),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke("1", list)
        assertEquals(false, result[0][0].isExpanded)
        assertEquals(false, result[0][1].isExpanded)
    }

    @Test
    fun noItemIsExpandedWhenIdIsNull() = runBlocking {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke(null, list)
        assertEquals(false, result[0][0].isExpanded)
        assertEquals(false, result[0][1].isExpanded)
    }

    @Test
    fun noItemIsExpandedWhenIdDoesNotMatch() = runBlocking {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke("3", list)
        assertEquals(false, result[0][0].isExpanded)
        assertEquals(false, result[0][1].isExpanded)
    }
}
