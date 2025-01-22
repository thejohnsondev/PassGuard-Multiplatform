package com.thejohnsondev.domain

import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class EncryptPasswordModelUseCaseImplTest {

    private lateinit var encryptionRepository: EncryptionRepository
    private lateinit var useCase: EncryptPasswordModelUseCaseImpl

    @BeforeTest
    fun setUp() {
        encryptionRepository = mockk()
        useCase = EncryptPasswordModelUseCaseImpl(encryptionRepository)
    }

    @Test
    fun invoke_encryptsAllFields() = runBlocking {
        val passwordDto = PasswordDto(
            id = "1",
            title = "Org",
            organizationLogo = "Logo",
            userName = "Title",
            password = "Password",
            additionalFields = listOf(
                AdditionalFieldDto(id = "1", title = "Field1", value = "Value1")
            ),
            createdTimeStamp = "Created",
            modifiedTimeStamp = "Modified",
            categoryId = "TestCategory"
        )

        coEvery { encryptionRepository.encrypt("Org") } returns "EncryptedOrg"
        coEvery { encryptionRepository.encrypt("Logo") } returns "EncryptedLogo"
        coEvery { encryptionRepository.encrypt("Title") } returns "EncryptedTitle"
        coEvery { encryptionRepository.encrypt("Password") } returns "EncryptedPassword"
        coEvery { encryptionRepository.encrypt("Field1") } returns "EncryptedField1"
        coEvery { encryptionRepository.encrypt("Value1") } returns "EncryptedValue1"
        coEvery { encryptionRepository.encrypt("Created") } returns "EncryptedCreated"
        coEvery { encryptionRepository.encrypt("Modified") } returns "EncryptedModified"

        val result = useCase.invoke(passwordDto)

        assertEquals("EncryptedOrg", result.title)
        assertEquals("EncryptedLogo", result.organizationLogo)
        assertEquals("EncryptedTitle", result.userName)
        assertEquals("EncryptedPassword", result.password)
        assertEquals("EncryptedCreated", result.createdTimeStamp)
        assertEquals("EncryptedModified", result.modifiedTimeStamp)
        assertEquals(1, result.additionalFields.size)
        val encryptedField = result.additionalFields[0]
        assertEquals("EncryptedField1", encryptedField.title)
        assertEquals("EncryptedValue1", encryptedField.value)
    }

    @Test
    fun invoke_handlesNullFields() = runBlocking {
        val passwordDto = PasswordDto(
            id = "1",
            title = "Org",
            organizationLogo = null,
            userName = "Title",
            password = "Password",
            additionalFields = emptyList(),
            createdTimeStamp = null,
            modifiedTimeStamp = null,
            categoryId = "TestCategory"
        )

        coEvery { encryptionRepository.encrypt("Org") } returns "EncryptedOrg"
        coEvery { encryptionRepository.encrypt("Title") } returns "EncryptedTitle"
        coEvery { encryptionRepository.encrypt("Password") } returns "EncryptedPassword"

        val result = useCase.invoke(passwordDto)

        assertEquals("EncryptedOrg", result.title)
        assertEquals(null, result.organizationLogo)
        assertEquals("EncryptedTitle", result.userName)
        assertEquals("EncryptedPassword", result.password)
        assertEquals(null, result.createdTimeStamp)
        assertEquals(null, result.modifiedTimeStamp)
        assertEquals(0, result.additionalFields.size)
    }

}