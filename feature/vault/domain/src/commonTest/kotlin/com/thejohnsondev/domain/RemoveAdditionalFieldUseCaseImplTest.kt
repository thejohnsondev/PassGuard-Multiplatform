package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoveAdditionalFieldUseCaseImplTest {

    private val useCase = RemoveAdditionalFieldUseCaseImpl()

    @Test
    fun invoke_removesFieldWithMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "Value1"),
            AdditionalFieldDto(id = "2", title = "Title2", value = "Value2")
        )
        val result = useCase.invoke("1", currentList)
        assertEquals(1, result.size)
        assertEquals("2", result[0].id)
        assertEquals("Title2", result[0].title)
        assertEquals("Value2", result[0].value)
    }

    @Test
    fun invoke_doesNotRemoveFieldWithNonMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "Value1"),
            AdditionalFieldDto(id = "2", title = "Title2", value = "Value2")
        )
        val result = useCase.invoke("3", currentList)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title1", result[0].title)
        assertEquals("Value1", result[0].value)
        assertEquals("2", result[1].id)
        assertEquals("Title2", result[1].title)
        assertEquals("Value2", result[1].value)
    }

    @Test
    fun invoke_handlesEmptyList() {
        val currentList = emptyList<AdditionalFieldDto>()
        val result = useCase.invoke("1", currentList)
        assertEquals(0, result.size)
    }

    @Test
    fun invoke_handlesMultipleFieldsWithSameId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "Value1"),
            AdditionalFieldDto(id = "1", title = "Title2", value = "Value2")
        )
        val result = useCase.invoke("1", currentList)
        assertEquals(0, result.size)
    }
}