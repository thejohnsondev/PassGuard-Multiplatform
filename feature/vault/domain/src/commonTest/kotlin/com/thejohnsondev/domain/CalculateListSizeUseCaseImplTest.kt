package com.thejohnsondev.domain

import com.thejohnsondev.domain.models.PasswordUIModel
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateListSizeUseCaseImplTest {

    private val useCase = CalculateListSizeUseCaseImpl()

    @Test
    fun testReturnsCorrectSizeWhenListContainsOnlyOneSublist() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        assertEquals(164, result) // 2 * 82
    }

    @Test
    fun testReturnsCorrectSizeWhenFirstSublistIsLargerThanSecond() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "3", isExpanded = false),
            ),
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "4", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        assertEquals(246, result) // 3 * 82
    }

    @Test
    fun testReturnsCorrectSizeWhenSecondSublistIsLargerThanFirst() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
            ),
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "3", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        assertEquals(164, result) // 2 * 82
    }

    @Test
    fun testReturnsCorrectSizeWhenBothSublistsAreOfEqualSize() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "1", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            ),
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(id = "3", isExpanded = false),
                PasswordUIModel.testPasswordUIModel.copy(id = "4", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        assertEquals(164, result) // 2 * 82
    }
}