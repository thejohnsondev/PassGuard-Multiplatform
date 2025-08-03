package com.thejohnsondev.domain.export

import com.thejohnsondev.domain.CheckPassDuplicatesUseCase
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CheckPassDuplicatesUseCaseTest {

    private val useCase = CheckPassDuplicatesUseCase()

    private fun password(title: String, user: String, pass: String) =
        PasswordDto.demo1.copy(title = title, userName = user, password = pass)

    @Test
    fun noDuplicatesReturnsAllInListWithoutDuplicates() {
        val saved = listOf(password("A", "user1", "pass1"))
        val new = listOf(password("B", "user2", "pass2"))
        val result = useCase(saved, new)
        assertTrue(result.duplicates.isEmpty())
        assertEquals(new, result.listWithoutDuplicates)
    }

    @Test
    fun allDuplicatesReturnsAllInDuplicates() {
        val saved = listOf(password("A", "user1", "pass1"))
        val new = listOf(password("A", "user1", "pass1"))
        val result = useCase(saved, new)
        assertEquals(new, result.duplicates)
        assertTrue(result.listWithoutDuplicates.isEmpty())
    }

    @Test
    fun someDuplicatesReturnsCorrectLists() {
        val saved = listOf(
            password("A", "user1", "pass1"),
            password("B", "user2", "pass2")
        )
        val new = listOf(
            password("A", "user1", "pass1"),
            password("C", "user3", "pass3")
        )
        val result = useCase(saved, new)
        assertEquals(listOf(password("A", "user1", "pass1")), result.duplicates)
        assertEquals(listOf(password("C", "user3", "pass3")), result.listWithoutDuplicates)
    }

    @Test
    fun emptySavedListReturnsAllNewAsListWithoutDuplicates() {
        val saved = emptyList<PasswordDto>()
        val new = listOf(password("A", "user1", "pass1"))
        val result = useCase(saved, new)
        assertTrue(result.duplicates.isEmpty())
        assertEquals(new, result.listWithoutDuplicates)
    }

    @Test
    fun emptyNewListReturnsEmptyLists() {
        val saved = listOf(password("A", "user1", "pass1"))
        val new = emptyList<PasswordDto>()
        val result = useCase(saved, new)
        assertTrue(result.duplicates.isEmpty())
        assertTrue(result.listWithoutDuplicates.isEmpty())
    }

    @Test
    fun identicalPasswordsWithDifferentFieldsAreNotDuplicates() {
        val saved = listOf(password("A", "user1", "pass1"))
        val new = listOf(password("A", "user2", "pass1"))
        val result = useCase(saved, new)
        assertTrue(result.duplicates.isEmpty())
        assertEquals(new, result.listWithoutDuplicates)
    }

    @Test
    fun multipleDuplicatesAreHandledCorrectly() {
        val saved = listOf(
            password("A", "user1", "pass1"),
            password("B", "user2", "pass2")
        )
        val new = listOf(
            password("A", "user1", "pass1"),
            password("B", "user2", "pass2"),
            password("C", "user3", "pass3")
        )
        val result = useCase(saved, new)
        assertEquals(
            listOf(
                password("A", "user1", "pass1"),
                password("B", "user2", "pass2")
            ),
            result.duplicates
        )
        assertEquals(listOf(password("C", "user3", "pass3")), result.listWithoutDuplicates)
    }
}