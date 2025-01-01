package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlin.test.Test
import kotlin.test.assertEquals

class EnterAdditionalFieldTitleUseCaseImplTest {

    private val useCase = EnterAdditionalFieldTitleUseCaseImpl()

    @Test
    fun invoke_updatesTitleForMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "OldTitle", value = "Value1")
        )
        val result = useCase.invoke("1", "NewTitle", currentList)
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("NewTitle", result[0].title)
        assertEquals("Value1", result[0].value)
    }

    @Test
    fun invoke_doesNotUpdateTitleForNonMatchingId() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "OldTitle", value = "Value1")
        )
        val result = useCase.invoke("2", "NewTitle", currentList)
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("OldTitle", result[0].title)
        assertEquals("Value1", result[0].value)
    }

    @Test
    fun invoke_updatesTitleForMultipleItems() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "OldTitle1", value = "Value1"),
            AdditionalFieldDto(id = "2", title = "OldTitle2", value = "Value2")
        )
        val result = useCase.invoke("2", "NewTitle2", currentList)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("OldTitle1", result[0].title)
        assertEquals("Value1", result[0].value)
        assertEquals("2", result[1].id)
        assertEquals("NewTitle2", result[1].title)
        assertEquals("Value2", result[1].value)
    }

    @Test
    fun invoke_handlesEmptyList() {
        val currentList = emptyList<AdditionalFieldDto>()
        val result = useCase.invoke("1", "NewTitle", currentList)
        assertEquals(0, result.size)
    }
}