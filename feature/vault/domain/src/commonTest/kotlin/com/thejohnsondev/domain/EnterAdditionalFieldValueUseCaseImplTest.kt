package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlin.test.Test
import kotlin.test.assertEquals

class EnterAdditionalFieldValueUseCaseImplTest {

    private val useCase = EnterAdditionalFieldValueUseCaseImpl()

    @Test
    fun invoke_updatesValueForMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "OldValue")
        )
        val result = useCase.invoke("1", "NewValue", currentList)
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title1", result[0].title)
        assertEquals("NewValue", result[0].value)
    }

    @Test
    fun invoke_doesNotUpdateValueForNonMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "OldValue")
        )
        val result = useCase.invoke("2", "NewValue", currentList)
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title1", result[0].title)
        assertEquals("OldValue", result[0].value)
    }

    @Test
    fun invoke_updatesValueForMultipleItems() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "OldValue1"),
            AdditionalFieldDto(id = "2", title = "Title2", value = "OldValue2")
        )
        val result = useCase.invoke("2", "NewValue2", currentList)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title1", result[0].title)
        assertEquals("OldValue1", result[0].value)
        assertEquals("2", result[1].id)
        assertEquals("Title2", result[1].title)
        assertEquals("NewValue2", result[1].value)
    }

    @Test
    fun invoke_handlesEmptyList() {
        val currentList = emptyList<AdditionalFieldDto>()
        val result = useCase.invoke("1", "NewValue", currentList)
        assertEquals(0, result.size)
    }
}