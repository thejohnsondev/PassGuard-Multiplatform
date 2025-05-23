package com.thejohnsondev.domain

import com.thejohnsondev.common.ADDITIONAL_FIELD_HEIGHT
import com.thejohnsondev.common.PASSWORD_EXPANDED_ITEM_HEIGHT
import com.thejohnsondev.common.PASSWORD_IDLE_ITEM_HEIGHT
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
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
        val expectedHeight = 2 * PASSWORD_IDLE_ITEM_HEIGHT
        assertEquals(expectedHeight, result)
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
        val expectedHeight = 3 * PASSWORD_IDLE_ITEM_HEIGHT
        assertEquals(expectedHeight, result)
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
        val expectedHeight = 2 * PASSWORD_IDLE_ITEM_HEIGHT
        assertEquals(expectedHeight, result)
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
        val expectedHeight = 2 * PASSWORD_IDLE_ITEM_HEIGHT
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenOneItemIsExpandedWithOneAdditionalField() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "1", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField
                    )
                )
            )
        )
        val result = useCase.invoke(list)
        val expectedHeight =
            (PASSWORD_IDLE_ITEM_HEIGHT + PASSWORD_EXPANDED_ITEM_HEIGHT) + ADDITIONAL_FIELD_HEIGHT
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenOneItemIsExpandedWithAdditionalFields() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "1", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField,
                        AdditionalFieldDto.testAdditionalField.copy(id = "2")
                    )
                )
            )
        )
        val result = useCase.invoke(list)
        val expectedHeight =
            (PASSWORD_IDLE_ITEM_HEIGHT + PASSWORD_EXPANDED_ITEM_HEIGHT) + (2 * ADDITIONAL_FIELD_HEIGHT)
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenItemIsExpandedWithAdditionalFields() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "1", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField,
                        AdditionalFieldDto.testAdditionalField.copy(id = "2")
                    )
                ),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        val expectedHeight =
            (PASSWORD_IDLE_ITEM_HEIGHT + PASSWORD_EXPANDED_ITEM_HEIGHT) + (2 * ADDITIONAL_FIELD_HEIGHT) + PASSWORD_IDLE_ITEM_HEIGHT
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenOneSublistHavingExpandedItems() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "1", isExpanded = false, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField
                    )
                ),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            ),
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "3", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField,
                        AdditionalFieldDto.testAdditionalField.copy(id = "2")
                    )
                ),
                PasswordUIModel.testPasswordUIModel.copy(id = "4", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        val expectedHeight =
            PASSWORD_IDLE_ITEM_HEIGHT + ((PASSWORD_IDLE_ITEM_HEIGHT + PASSWORD_EXPANDED_ITEM_HEIGHT) + (ADDITIONAL_FIELD_HEIGHT * 2))
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenBothSublistsHavingExpandedItems() {
        val list = listOf(
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "1", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField
                    )
                ),
                PasswordUIModel.testPasswordUIModel.copy(id = "2", isExpanded = false),
            ),
            listOf(
                PasswordUIModel.testPasswordUIModel.copy(
                    id = "3", isExpanded = true, additionalFields = listOf(
                        AdditionalFieldDto.testAdditionalField,
                        AdditionalFieldDto.testAdditionalField.copy(id = "2")
                    )
                ),
                PasswordUIModel.testPasswordUIModel.copy(id = "4", isExpanded = false),
            )
        )
        val result = useCase.invoke(list)
        val expectedHeight =
            PASSWORD_IDLE_ITEM_HEIGHT + ((PASSWORD_IDLE_ITEM_HEIGHT + PASSWORD_EXPANDED_ITEM_HEIGHT) + (ADDITIONAL_FIELD_HEIGHT * 2))
        assertEquals(expectedHeight, result)
    }

    @Test
    fun testReturnsCorrectSizeWhenListIsEmpty() {
        val list = emptyList<List<PasswordUIModel>>()
        val result = useCase.invoke(list)
        val expectedHeight = 0
        assertEquals(expectedHeight, result)
    }
}