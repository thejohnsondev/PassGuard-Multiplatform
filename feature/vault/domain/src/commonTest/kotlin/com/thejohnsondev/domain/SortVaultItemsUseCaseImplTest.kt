package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SortVaultItemsUseCaseImplTest {

    private val sortVaultItemsUseCase = SortVaultItemsUseCaseImpl()

    private val item1 = PasswordUIModel.testPasswordUIModel.copy(
        id = "1",
        title = "Item 1",
        organization = "Org A",
        createdTime = "Jan 20 at 15:14",
        modifiedTime = "Jan 20 at 15:15",
        isFavorite = false
    )

    private val item2 = PasswordUIModel.testPasswordUIModel.copy(
        id = "2",
        title = "Item 2",
        organization = "Org B",
        createdTime = "Jan 20 at 15:14",
        modifiedTime = null,
        isFavorite = true
    )

    private val item3 = PasswordUIModel.testPasswordUIModel.copy(
        id = "3",
        title = "Item 3",
        organization = "Org C",
        createdTime = "Jan 20 at 15:13",
        modifiedTime = "Jan 20 at 15:16",
        isFavorite = false
    )

    private val unsortedList = listOf(item1, item2, item3)

    @Test
    fun sort_dateDesc() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.DATE_DESC, false)
        val expected = listOf(item3, item1, item2)
        assertEquals(expected, result)
    }

    @Test
    fun sort_dateAsc() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.DATE_ASC, false)
        val expected = listOf(item2, item1, item3)
        assertEquals(expected, result)
    }

    @Test
    fun sort_alphabeticalDesc() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.ALPHABETICAL_DESC, false)
        val expected = listOf(item3, item2, item1)
        assertEquals(expected, result)
    }

    @Test
    fun sort_alphabeticalAsc() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.ALPHABETICAL_ASC, false)
        val expected = listOf(item1, item2, item3)
        assertEquals(expected, result)
    }

    @Test
    fun sort_dateDesc_keepFavoriteAtTop() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.DATE_DESC, true)
        val expected = listOf(item2, item3, item1)
        assertEquals(expected, result)
    }

    @Test
    fun sort_dateAsc_keepFavoriteAtTop() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.DATE_ASC, true)
        val expected = listOf(item2, item1, item3)
        assertEquals(expected, result)
    }

    @Test
    fun sort_alphabeticalDesc_keepFavoriteAtTop() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.ALPHABETICAL_DESC, true)
        val expected = listOf(item2, item3, item1)
        assertEquals(expected, result)
    }

    @Test
    fun sort_alphabeticalAsc_keepFavoriteAtTop() = runBlocking {
        val result = sortVaultItemsUseCase(unsortedList, SortOrder.ALPHABETICAL_ASC, true)
        val expected = listOf(item2, item1, item3)
        assertEquals(expected, result)
    }

}