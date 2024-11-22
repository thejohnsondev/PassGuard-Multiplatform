package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel
import com.thejohnsondev.model.vault.AdditionalFieldModel
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchItemsUseCaseImplTest {

    private val useCase = SearchItemsUseCaseImpl()

    @Test
    fun testReturnsOriginalListWhenQueryIsEmpty() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                title = "Title1",
                organization = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                title = "Title2",
                organization = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("", false, list)
        assertEquals(list, result)
    }

    @Test
    fun testReturnsFilteredListWhenDeepSearchDisabledAndQueryMatchesTitleOrOrganization() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                title = "Title1",
                organization = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                title = "Title2",
                organization = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("Title1", false, list)
        assertEquals(listOf(list[0]), result)
    }

    @Test
    fun testReturnsFilteredListWhenDeepSearchEnabledAndQueryMatchesTitleOrganizationPasswordOrAdditionalFields() {
        val list = listOf(
            PasswordUIModel.testPasswordUIModel.copy(
                id = "1",
                title = "Title1",
                organization = "Org1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldModel(id = "1", title = "Field1", value = "Value1")
                )
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                title = "Title2",
                organization = "Org2",
                password = "pass2",
                additionalFields = listOf(
                    AdditionalFieldModel(id = "2", title = "Field2", value = "Value2")
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
                title = "Title1",
                organization = "Org1",
                password = "pass1",
                additionalFields = emptyList()
            ),
            PasswordUIModel.testPasswordUIModel.copy(
                id = "2",
                title = "Title2",
                organization = "Org2",
                password = "pass2",
                additionalFields = emptyList()
            )
        )
        val result = useCase.invoke("NonExistentQuery", false, list)
        assertEquals(emptyList(), result)
    }

}