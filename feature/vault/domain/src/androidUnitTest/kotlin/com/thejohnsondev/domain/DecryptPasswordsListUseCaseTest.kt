package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DecryptPasswordsListUseCaseTest {


    private lateinit var encryptionRepository: EncryptionRepository
    private lateinit var useCase: DecryptPasswordsListUseCase

    @BeforeTest
    fun setUp() {
        encryptionRepository = mockk()
        useCase = DecryptPasswordsListUseCase(encryptionRepository)
    }

    @Test
    fun invoke_decryptsAllFields() = runBlocking {
        val encryptedPasswords = listOf(
            PasswordDto(
                id = "1",
                title = "encryptedOrg",
                organizationLogo = "encryptedLogo",
                userName = "encryptedTitle",
                password = "encryptedPassword",
                additionalFields = listOf(
                    AdditionalFieldDto(id = "1", title = "encryptedTitle1", value = "encryptedValue1")
                ),
                createdTimeStamp = "encryptedCreated",
                modifiedTimeStamp = "encryptedModified",
                categoryId = "TestCategory"
            )
        )

        coEvery { encryptionRepository.decrypt("encryptedOrg") } returns "decryptedOrg"
        coEvery { encryptionRepository.decrypt("encryptedLogo") } returns "decryptedLogo"
        coEvery { encryptionRepository.decrypt("encryptedTitle") } returns "decryptedTitle"
        coEvery { encryptionRepository.decrypt("encryptedPassword") } returns "decryptedPassword"
        coEvery { encryptionRepository.decrypt("encryptedTitle1") } returns "decryptedTitle1"
        coEvery { encryptionRepository.decrypt("encryptedValue1") } returns "decryptedValue1"
        coEvery { encryptionRepository.decrypt("encryptedCreated") } returns "decryptedCreated"
        coEvery { encryptionRepository.decrypt("encryptedModified") } returns "decryptedModified"

        val result = useCase.invoke(encryptedPasswords)

        assertEquals(1, result.size)
        val decryptedPassword = result[0]
        assertEquals("decryptedOrg", decryptedPassword.title)
        assertEquals("decryptedLogo", decryptedPassword.organizationLogo)
        assertEquals("decryptedTitle", decryptedPassword.userName)
        assertEquals("decryptedPassword", decryptedPassword.password)
        assertEquals("decryptedCreated", decryptedPassword.createdTimeStamp)
        assertEquals("decryptedModified", decryptedPassword.modifiedTimeStamp)
        assertEquals(1, decryptedPassword.additionalFields.size)
        val decryptedField = decryptedPassword.additionalFields[0]
        assertEquals("decryptedTitle1", decryptedField.title)
        assertEquals("decryptedValue1", decryptedField.value)
    }

    @Test
    fun invoke_handlesEmptyList() = runBlocking {
        val encryptedPasswords = emptyList<PasswordDto>()
        val result = useCase.invoke(encryptedPasswords)
        assertEquals(0, result.size)
    }

    @Test
    fun invoke_handlesNullFields() = runBlocking {
        val encryptedPasswords = listOf(
            PasswordDto(
                id = "1",
                title = "encryptedOrg",
                organizationLogo = null,
                userName = "encryptedTitle",
                password = "encryptedPassword",
                additionalFields = emptyList(),
                createdTimeStamp = null,
                modifiedTimeStamp = null,
                categoryId = "TestCategory"
            )
        )

        coEvery { encryptionRepository.decrypt("encryptedOrg") } returns "decryptedOrg"
        coEvery { encryptionRepository.decrypt("encryptedTitle") } returns "decryptedTitle"
        coEvery { encryptionRepository.decrypt("encryptedPassword") } returns "decryptedPassword"

        val result = useCase.invoke(encryptedPasswords)

        assertEquals(1, result.size)
        val decryptedPassword = result[0]
        assertEquals("decryptedOrg", decryptedPassword.title)
        assertEquals(null, decryptedPassword.organizationLogo)
        assertEquals("decryptedTitle", decryptedPassword.userName)
        assertEquals("decryptedPassword", decryptedPassword.password)
        assertEquals(null, decryptedPassword.createdTimeStamp)
        assertEquals(null, decryptedPassword.modifiedTimeStamp)
        assertEquals(0, decryptedPassword.additionalFields.size)
    }

}