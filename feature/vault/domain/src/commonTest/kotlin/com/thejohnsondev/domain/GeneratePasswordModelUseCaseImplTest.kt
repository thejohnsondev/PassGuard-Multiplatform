package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GeneratePasswordModelUseCaseImplTest {

    private lateinit var useCase: GeneratePasswordModelUseCaseImpl

    @BeforeTest
    fun setUp() {
        useCase = GeneratePasswordModelUseCaseImpl()
    }

    @Test
    fun invoke_generatesPasswordModelWithNewId() {
        val title = "TestOrg"
        val userName = "TestTitle"
        val password = "TestPassword"
        val categoryId = "TestCategory"
        val additionalFields = listOf<AdditionalFieldDto>()
        val isFavorite = false

        val result = useCase.invoke(
            passwordId = null,
            title = title,
            userName = userName,
            password = password,
            categoryId = categoryId,
            additionalFields = additionalFields,
            createdTime = null,
            isFavorite = isFavorite
        )

        assertEquals(title, result.title)
        assertEquals(userName, result.userName)
        assertEquals(password, result.password)
        assertEquals(categoryId, result.categoryId)
        assertEquals(additionalFields, result.additionalFields)
        assertEquals(isFavorite, result.isFavorite)
        assertEquals(null, result.modifiedTimeStamp)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun invoke_generatesPasswordModelWithProvidedId() {
        val passwordId = Uuid.random().toString()
        val title = "TestOrg"
        val userName = "TestTitle"
        val password = "TestPassword"
        val categoryId = "TestCategory"
        val additionalFields = listOf<AdditionalFieldDto>()
        val createdTime = getCurrentTimeStamp()
        val isFavorite = false

        val result = useCase.invoke(
            passwordId = passwordId,
            title = title,
            userName = userName,
            password = password,
            categoryId = categoryId,
            additionalFields = additionalFields,
            createdTime = createdTime,
            isFavorite = isFavorite
        )

        assertEquals(passwordId, result.id)
        assertEquals(title, result.title)
        assertEquals(userName, result.userName)
        assertEquals(password, result.password)
        assertEquals(categoryId, result.categoryId)
        assertEquals(additionalFields, result.additionalFields)
        assertEquals(createdTime, result.createdTimeStamp)
        assertEquals(result.createdTimeStamp, result.modifiedTimeStamp)
        assertEquals(isFavorite, result.isFavorite)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun invoke_generatesPasswordModelWithNullModifiedTime() {
        val passwordId = Uuid.random().toString()
        val title = "TestOrg"
        val userName = "TestTitle"
        val password = "TestPassword"
        val categoryId = "TestCategory"
        val additionalFields = listOf<AdditionalFieldDto>()
        val createdTime = null
        val isFavorite = false

        val result = useCase.invoke(
            passwordId = passwordId,
            title = title,
            userName = userName,
            password = password,
            categoryId = categoryId,
            additionalFields = additionalFields,
            createdTime = createdTime,
            isFavorite = isFavorite
        )

        assertEquals(passwordId, result.id)
        assertEquals(title, result.title)
        assertEquals(userName, result.userName)
        assertEquals(password, result.password)
        assertEquals(categoryId, result.categoryId)
        assertEquals(additionalFields, result.additionalFields)
        assertEquals(isFavorite, result.isFavorite)
        assertEquals(null, result.modifiedTimeStamp)
    }

}