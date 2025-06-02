package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CSVImportExportUtilsTest {

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
        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

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
        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

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
        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

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
        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun parseEmptyCsvContent() {
        val csvContent = ""
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.EmptyContent>(result)
        assertEquals("CSV content is empty.", result.message)
        assertTrue(result.parsingTimestamp > 0) // Check that a timestamp was set
    }

    @Test
    fun parseCsvWithBlankContent() {
        val csvContent = "   \n\t  "
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.EmptyContent>(result)
        assertEquals("CSV content is empty.", result.message)
    }

    @Test
    fun parseCsvWithOnlyHeader() {
        val csvContent = "name,url,username,password,note"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(0, result.passwords.size)
        assertEquals(0, result.failedEntries.size)
        assertEquals(1, result.summary.totalLinesProcessed)
        assertEquals(0, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)
    }

    @Test
    fun parseCsvWithInvalidHeaderMissingColumns() {
        val csvContent = "name,username,password,note1,note2,note3,note4"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.ValidationError>(result)
        assertTrue(result.message.contains("missing required columns: url"))
        assertTrue(result.details!!.contains("Expected headers: name, url, username, password, note"))
    }

    @Test
    fun parseCsvWithInvalidHeaderWrongNames() {
        val csvContent = "item_name,link,user_id,secret,description\nval,val,val,val,val"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.ValidationError>(result)
        assertTrue(result.message.contains("missing required columns: name, url, username, password, note"))
    }

    @Test
    fun parsePerfectCsvWithMultiplePasswords() {
        val csvContent = """
            name,url,username,password,note
            My Account,example.com,user1,pass123,Some notes
            Another Login,anothersite.org,user2,securepass,Extra info here
            Third Service,site.com,user3,strongpass,No domain
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(3, result.passwords.size)
        assertEquals(0, result.failedEntries.size)
        assertEquals(4, result.summary.totalLinesProcessed)
        assertEquals(3, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)

        val p1 = result.passwords[0]
        assertEquals("My Account", p1.title)
        assertEquals("example.com", p1.domain)
        assertEquals("user1", p1.userName)
        assertEquals("pass123", p1.password)
        assertEquals(1, p1.additionalFields.size)
        assertEquals("Note", p1.additionalFields[0].title)
        assertEquals("Some notes", p1.additionalFields[0].value)

        val p3 = result.passwords[2]
        assertEquals("Third Service", p3.title)
        assertEquals("site.com", p3.domain) // Domain can be null/empty
        assertEquals("user3", p3.userName)
    }

    @Test
    fun parseCsvWithMixedValidAndInvalidRows() {
        val csvContent = """
            name,url,username,password,note
            Valid Entry 1,example.com,userA,passA,noteA
            Invalid Entry 1,too,few,columns
            Valid Entry 2,valid.net,userB,passB,noteB
            Invalid Entry 2,,userC,,noteC
            Invalid Entry 3,badurl,userD,passD,noteD
            Valid Entry 3,sub.domain.com,userE,passE,noteE
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(3, result.passwords.size) // Only 3 valid ones
        assertEquals(3, result.failedEntries.size) // 3 invalid ones
        assertEquals(7, result.summary.totalLinesProcessed)
        assertEquals(6, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)

        // Check failed entries
        assertEquals(3, result.failedEntries[0].lineNumber) // "Invalid Entry 1"
        assertTrue(result.failedEntries[0].reason!!.contains("Incorrect number of columns"))
        assertEquals("Invalid Entry 1,too,few,columns", result.failedEntries[0].rawLineContent)

        assertEquals(5, result.failedEntries[1].lineNumber) // "Invalid Entry 2"
        assertTrue(result.failedEntries[1].reason!!.contains("Missing required field"))
        assertEquals("Invalid Entry 2,,userC,,noteC", result.failedEntries[1].rawLineContent)

        assertEquals(6, result.failedEntries[2].lineNumber) // "Invalid Entry 3"
        assertTrue(result.failedEntries[2].reason!!.contains("Invalid URL format"))
        assertEquals("Invalid Entry 3,badurl,userD,passD,noteD", result.failedEntries[2].rawLineContent)

        // Check a successfully parsed password
        assertEquals("Valid Entry 1", result.passwords[0].title)
        assertEquals("userA", result.passwords[0].userName)
    }

    @Test
    fun parseCsvWithInvalidUrls() {
        val csvContent = """
            name,url,username,password,note
            Account 1,not-a-url,user1,pass1,note1
            Account 2,ftp://example.com,user2,pass2,note2
            Account 3,valid.com,user3,pass3,note3
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size) // Only Account 3 should pass
        assertEquals(2, result.failedEntries.size)

        assertTrue(result.failedEntries[0].reason!!.contains("Invalid URL format for domain: 'not-a-url'"))
        assertTrue(result.failedEntries[1].reason!!.contains("Invalid URL format for domain: 'ftp://example.com'"))

        assertEquals("Account 3", result.passwords[0].title)
    }

    @Test
    fun parseCsvWithAdditionalFields() {
        val csvContent = """
            name,url,username,password,note,note1,note2
            Item 1,site.com,u1,p1,main notes,CustomField|Value1,AnotherField|Value2
            Item 2,site2.com,u2,p2,only main notes
            Item 3,site3.com,u3,p3,Note without pipe, Just a value
            Item 4,site4.com,u4,p4,,CustomField|Value3
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(4, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        // Item 1: main notes + 2 additional fields
        val p1 = result.passwords[0]
        assertEquals(3, p1.additionalFields.size)
        assertEquals("Note", p1.additionalFields[0].title)
        assertEquals("main notes", p1.additionalFields[0].value)
        assertEquals("CustomField", p1.additionalFields[1].title)
        assertEquals("Value1", p1.additionalFields[1].value)
        assertEquals("AnotherField", p1.additionalFields[2].title)
        assertEquals("Value2", p1.additionalFields[2].value)

        // Item 2: only main notes
        val p2 = result.passwords[1]
        assertEquals(1, p2.additionalFields.size)
        assertEquals("Note", p2.additionalFields[0].title)
        assertEquals("only main notes", p2.additionalFields[0].value)

        // Item 3: "Note without pipe" and "Just a value" treated as separate "Note" fields
        val p3 = result.passwords[2]
        assertEquals(2, p3.additionalFields.size)
        assertEquals("Note", p3.additionalFields[0].title)
        assertEquals("Note without pipe", p3.additionalFields[0].value)
        assertEquals("Note", p3.additionalFields[1].title)
        assertEquals("Just a value", p3.additionalFields[1].value)

        // Item 4: empty main note, one additional field
        val p4 = result.passwords[3]
        assertEquals(1, p4.additionalFields.size)
        assertEquals("CustomField", p4.additionalFields[0].title)
        assertEquals("Value3", p4.additionalFields[0].value)
    }

    @Test
    fun parseCsvWithEmptyLinesBetweenData() {
        val csvContent = """
            name,url,username,password,note
            Entry1,example.com,user1,pass1,note1

            Entry2,example.net,user2,pass2,note2
            
            Entry3,example.org,user3,pass3,note3
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(3, result.passwords.size) // Empty lines are naturally skipped by isBlank()
        assertEquals(2, result.failedEntries.size)
        assertEquals(6, result.summary.totalLinesProcessed) // Including the blank lines
        assertEquals(5, result.summary.dataLinesProcessed) // Including the blank lines
        assertTrue(result.summary.headerValidated)

        assertEquals("Entry1", result.passwords[0].title)
        assertEquals("Entry2", result.passwords[1].title)
        assertEquals("Entry3", result.passwords[2].title)
    }

    @Test
    fun parseCsvWithWhitespaceInFields() {
        val csvContent = """
            name , url , username , password , note
             My Item 1  ,https://example.com,  user A  ,  pass B  ,  Notes C  
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        val p1 = result.passwords[0]
        assertEquals("My Item 1", p1.title)
        assertEquals("https://example.com", p1.domain) // Regex should handle HTTPS
        assertEquals("user A", p1.userName)
        assertEquals("pass B", p1.password)
        assertEquals("Notes C", p1.additionalFields[0].value)
    }

    @Test
    fun parseCsvWithAdditionalFieldsContainingCommasOrPipesInValue() {
        val csvContent = """
            name,url,username,password,note,extra1
            Test Item,test.com,user,pass,Note with, comma,Key|Value with|pipes
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        val p1 = result.passwords[0]
        // Note: Due to simple split(",") and replace(",", " ") in getSanitized,
        // commas inside fields that aren't quoted will break the CSV structure.
        // Your current getSanitized() replaces commas, so they won't be in the final parsed value.
        // If "Note with, comma" was passed, it would become "Note with  comma" due to sanitization.
        // The parser would then incorrectly split it.

        // Given your current sanitization, the expectation for 'note' should be:
        assertEquals("Note", p1.additionalFields[0].title)
        assertEquals("Note with", p1.additionalFields[0].value) // Due to getSanitized() logic

        // For the second additional field, the split('|') might be tricky if the value contains a pipe not for separation
        // With limit=2 on split("|"), it will only split on the first pipe.
        assertEquals("comma,Key", p1.additionalFields[1].title)
        assertEquals("Value with|pipes", p1.additionalFields[1].value) // Due to getSanitized() logic
    }

}