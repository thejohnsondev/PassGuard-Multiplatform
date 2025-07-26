package com.thejohnsondev.domain

import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PasswordsMapToUiModelsUseCaseTest {

    private lateinit var useCase: PasswordsMapToUiModelsUseCase

    @BeforeTest
    fun setUp() {
        useCase = PasswordsMapToUiModelsUseCase()
    }

    @Test
    fun invoke_mapsPasswordsToUiModels() {
        val passwordsDto = listOf(
            PasswordDto(
                id = "1",
                title = "Org1",
                organizationLogo = "Logo1",
                userName = "Title1",
                password = "Password1",
                additionalFields = listOf(
                    AdditionalFieldDto(id = "1", title = "Field1", value = "Value1")
                ),
                createdTimeStamp = "Created1",
                modifiedTimeStamp = "Modified1",
                isFavorite = true,
                categoryId = VAULT_ITEM_CATEGORY_PERSONAL
            )
        )

        val result = useCase.invoke(passwordsDto)

        assertEquals(1, result.size)
        val uiModel = result[0]
        assertEquals("1", uiModel.id)
        assertEquals("Org1", uiModel.title)
        assertEquals("Logo1", uiModel.organizationLogo)
        assertEquals("Title1", uiModel.userName)
        assertEquals("Password1", uiModel.password)
        assertEquals(1, uiModel.additionalFields.size)
        assertEquals("Created1", uiModel.createdTime)
        assertEquals("Modified1", uiModel.modifiedTime)
        assertEquals(true, uiModel.isFavorite)
        assertEquals(VAULT_ITEM_CATEGORY_PERSONAL, uiModel.category.id)
    }

    @Test
    fun invoke_handlesInvalidCategoryId() {
        val passwordsDto = listOf(
            PasswordDto(
                id = "1",
                title = "Org1",
                organizationLogo = "Logo1",
                userName = "Title1",
                password = "Password1",
                additionalFields = listOf(
                    AdditionalFieldDto(id = "1", title = "Field1", value = "Value1")
                ),
                createdTimeStamp = "Created1",
                modifiedTimeStamp = "Modified1",
                isFavorite = true,
                categoryId = "invalid"
            )
        )

        assertFailsWith<IllegalArgumentException> {
            useCase.invoke(passwordsDto)
        }
    }

    @Test
    fun invoke_handlesEmptyList() {
        val passwordsDto = emptyList<PasswordDto>()
        val result = useCase.invoke(passwordsDto)
        assertEquals(0, result.size)
    }

}