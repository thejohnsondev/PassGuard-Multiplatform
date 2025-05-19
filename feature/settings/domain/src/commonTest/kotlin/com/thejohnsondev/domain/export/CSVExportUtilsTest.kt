package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.Test
import kotlin.test.assertEquals

class CSVExportUtilsTest {

    @Test
    fun generateCsvContentForPasswordsReturnsCorrectCsvContentForValidInput() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1"
            ),
            PasswordDto.demo1.copy(
                title = "Facebook",
                domain = "facebook.com",
                userName = "user2",
                password = "pass2"
            )
        )
        val expectedCsv = """
            name,url,username,password
            Google,https://google.com,user1,pass1
            Facebook,https://facebook.com,user2,pass2
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertEquals(expectedCsv, result)
    }

    @Test
    fun generateCsvContentForPasswordsHandlesSpecialCharactersInInput() {
        // Arrange
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "My,Title",
                domain = "example,domain.com",
                userName = "user,name",
                password = "pass,word"
            )
        )
        val expectedCsv = """
            name,url,username,password
            My Title,https://example domain.com,user name,pass word
        """.trimIndent()

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertEquals(expectedCsv, result)
    }

    @Test
    fun generateCsvContentForPasswordsHandlesEmptyPasswordList() {
        // Arrange
        val passwords = emptyList<PasswordDto>()
        val expectedCsv = "name,url,username,password"

        // Act
        val result = CSVExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertEquals(expectedCsv, result)
    }

}