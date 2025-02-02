package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.model.PasswordUIModel
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchItemsUseCaseImplTest {

    private val useCase = SearchItemsUseCaseImpl()

    @Test
    fun testReturnsOriginalListWhenQueryIsEmpty() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                userName = "Title1",
                title = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                userName = "Title2",
                title = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("", false, list)
        assertEquals(list, result)
    }

    @Test
    fun testReturnsFilteredListWhenDeepSearchDisabledAndQueryMatchesTitleOrUsername() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                userName = "Title1",
                title = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                userName = "Title2",
                title = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("Title1", false, list)
        assertEquals(listOf(list[0]), result)
    }

    @Test
    fun testReturnsFilteredListWhenDeepSearchEnabledAndQueryMatchesTitleUsernamePasswordOrAdditionalFields() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                userName = "Title1",
                title = "Org1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto(id = "1", title = "Field1", value = "Value1")
                )
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                userName = "Title2",
                title = "Org2",
                password = "pass2",
                additionalFields = listOf(
                    AdditionalFieldDto(id = "2", title = "Field2", value = "Value2")
                )
            )
        )
        val result = useCase.invoke("Value1", true, list)
        assertEquals(listOf(list[0]), result)
    }

    @Test
    fun testReturnsEmptyListWhenNoMatchesFound() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                userName = "Title1",
                title = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                userName = "Title2",
                title = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("NonExistentQuery", false, list)
        assertEquals(emptyList(), result)
    }

}