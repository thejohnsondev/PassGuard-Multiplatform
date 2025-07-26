package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlin.test.Test
import kotlin.test.assertEquals

class AddAdditionalFieldUseCaseTest {

    private val useCase = AddAdditionalFieldUseCase()

    @Test
    fun invoke_addsNewFieldToEmptyList() {
        val currentList = emptyList<AdditionalFieldDto>()
        val result = useCase.invoke(currentList)
        assertEquals(1, result.size)
        assertEquals("", result[0].title)
        assertEquals("", result[0].value)
    }

    @Test
    fun invoke_addsNewFieldToNonEmptyList() {
        val currentList = listOf(
            AdditionalFieldDto(id = "1", title = "Title1", value = "Value1")
        )
        val result = useCase.invoke(currentList)
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Title1", result[0].title)
        assertEquals("Value1", result[0].value)
        assertEquals("", result[1].title)
        assertEquals("", result[1].value)
    }

}