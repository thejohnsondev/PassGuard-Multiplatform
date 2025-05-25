package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CSVExportUtilsTest {

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithoutNotes() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = emptyList()
            )
        )
        val expectedCsv = """
            name,url,username,password,note
            Google,https://google.com,user1,pass1,
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithOneNote() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1")
                )
            )
        )
        val expectedCsv = """
            name,url,username,password,note,note1
            Google,https://google.com,user1,pass1,Note1|Value1
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithMultipleNotes() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note2", value = "Value2")
                )
            )
        )
        val expectedCsv = """
            name,url,username,password,note,note1,note2
            Google,https://google.com,user1,pass1,Note1|Value1,Note2|Value2
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithDifferentNotesNumber() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note2", value = "Value2")
                )
            ),
            PasswordDto.demo1.copy(
                title = "Google2",
                domain = "google.com",
                userName = "user2",
                password = "pass2",
                additionalFields = emptyList()
            ),
            PasswordDto.demo1.copy(
                title = "Google3",
                domain = "google.com",
                userName = "user3",
                password = "pass3",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note2", value = "Value2"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note3", value = "Value3"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note4", value = "Value4")
                )
            )
        )
        val expectedCsv = """
            name,url,username,password,note,note1,note2,note3,note4
            Google,https://google.com,user1,pass1,Note1|Value1,Note2|Value2
            Google2,https://google.com,user2,pass2,
            Google3,https://google.com,user3,pass3,Note1|Value1,Note2|Value2,Note3|Value3,Note4|Value4
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

}