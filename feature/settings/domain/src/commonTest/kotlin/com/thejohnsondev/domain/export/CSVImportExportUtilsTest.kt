package com.thejohnsondev.domain.export

import com.thejohnsondev.common.empty
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CSVImportExportUtilsTest {

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithoutNotesAndLogo() {
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = emptyList(),
                organizationLogo = null
            )
        )
        val expectedCsv = """
            name,url,username,password,note,logoUrl
            Google,https://google.com,user1,pass1,,,
        """.trimIndent()

        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithLogoButNoNotes() {
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "MySite",
                domain = "mysite.com",
                userName = "myuser",
                password = "mypass",
                additionalFields = emptyList(),
                organizationLogo = "https://mysite.com/logo.png"
            )
        )
        val expectedCsv = """
            name,url,username,password,note,logoUrl
            MySite,https://mysite.com,myuser,mypass,,https://mysite.com/logo.png,
        """.trimIndent()

        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithOneNoteAndLogo() {
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1")
                ),
                organizationLogo = "https://google.com/logo.svg"
            )
        )
        val expectedCsv = """
            name,url,username,password,note,logoUrl,note1
            Google,https://google.com,user1,pass1,Note1|Value1,https://google.com/logo.svg,
        """.trimIndent()

        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithMultipleNotesAndLogo() {
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note2", value = "Value2")
                ),
                organizationLogo = "https://google.com/logo.png"
            )
        )
        val expectedCsv = """
            name,url,username,password,note,logoUrl,note1,note2
            Google,https://google.com,user1,pass1,Note1|Value1,https://google.com/logo.png,Note2|Value2
        """.trimIndent()

        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

        assertTrue(result.isSuccessful)
        assertEquals(expectedCsv, result.csvContent)
        assertTrue(result.notExportedPasswords.isEmpty())
    }

    @Test
    fun generateCsvContentForPasswordsHandlesPasswordsWithDifferentNotesNumberAndOptionalLogo() {
        val passwords = listOf(
            PasswordDto.demo1.copy(
                title = "Google",
                domain = "google.com",
                userName = "user1",
                password = "pass1",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note1", value = "Value1"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "Note2", value = "Value2")
                ),
                organizationLogo = "https://google.com/icon.png"
            ),
            PasswordDto.demo1.copy(
                title = "Google2",
                domain = "google2.com",
                userName = "user2",
                password = "pass2",
                additionalFields = emptyList(),
                organizationLogo = null
            ),
            PasswordDto.demo1.copy(
                title = "Google3",
                domain = "google3.com",
                userName = "user3",
                password = "pass3",
                additionalFields = listOf(
                    AdditionalFieldDto.testAdditionalField.copy(title = "NoteA", value = "ValueA"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "NoteB", value = "ValueB"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "NoteC", value = "ValueC"),
                    AdditionalFieldDto.testAdditionalField.copy(title = "NoteD", value = "ValueD")
                ),
                organizationLogo = "https://google3.com/logo.jpg"
            )
        )
        val expectedCsv = """
            name,url,username,password,note,logoUrl,note1,note2,note3,note4
            Google,https://google.com,user1,pass1,Note1|Value1,https://google.com/icon.png,Note2|Value2
            Google2,https://google2.com,user2,pass2,,,
            Google3,https://google3.com,user3,pass3,NoteA|ValueA,https://google3.com/logo.jpg,NoteB|ValueB,NoteC|ValueC,NoteD|ValueD
        """.trimIndent()

        val result = CSVImportExportUtils.generateCsvContentForPasswords(passwords)

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
        assertTrue(result.parsingTimestamp > 0)
    }

    @Test
    fun parseCsvWithBlankContent() {
        val csvContent = "   \n\t  "
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.EmptyContent>(result)
        assertEquals("CSV content is empty.", result.message)
    }

    @Test
    fun parseCsvWithOnlyHeaderIncludingLogoUrl() {
        val csvContent = "name,url,username,password,note,logoUrl"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(0, result.passwords.size)
        assertEquals(0, result.failedEntries.size)
        assertEquals(1, result.summary.totalLinesProcessed)
        assertEquals(0, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)
    }

    @Test
    fun parseCsvWithInvalidHeaderMissingRequiredColumnsIncludingLogoUrl() {
        val csvContent = "name,username,password,note\nvalue1,value2,value3,value4"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.ValidationError>(result)
        assertTrue(result.message.contains("missing required columns: url"))
        assertTrue(result.details!!.contains("Expected headers: name, url, username, password, note"))
    }

    @Test
    fun parseCsvWithInvalidHeaderWrongNames() {
        val csvContent = "item_name,link,user_id,secret,description,image_url\nval,val,val,val,val,val"
        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.ValidationError>(result)
        assertTrue(result.message.contains("missing required columns: name, url, username, password, note"))
    }

    @Test
    fun parsePerfectCsvWithMultiplePasswordsAndOptionalLogo() {
        val csvContent = """
            name,url,username,password,note,logoUrl
            My Account,example.com,user1,pass123,Some notes,https://example.com/logo.png
            Another Login,anothersite.org,user2,securepass,Extra info here,
            Third Service,site.com,user3,strongpass,,https://site.com/icon.svg
            Fourth Service,test.net,user4,pass4,Another note,
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(4, result.passwords.size)
        assertEquals(0, result.failedEntries.size)
        assertEquals(5, result.summary.totalLinesProcessed)
        assertEquals(4, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)

        val p1 = result.passwords[0]
        assertEquals("My Account", p1.title)
        assertEquals("example.com", p1.domain)
        assertEquals("user1", p1.userName)
        assertEquals("pass123", p1.password)
        assertEquals(1, p1.additionalFields.size)
        assertEquals("Note", p1.additionalFields[0].title)
        assertEquals("Some notes", p1.additionalFields[0].value)
        assertEquals("https://example.com/logo.png", p1.organizationLogo)

        val p2 = result.passwords[1]
        assertEquals("Another Login", p2.title)
        assertEquals("anothersite.org", p2.domain)
        assertEquals(String.empty, p2.organizationLogo)

        val p3 = result.passwords[2]
        assertEquals("Third Service", p3.title)
        assertEquals("site.com", p3.domain)
        assertTrue(p3.additionalFields.isEmpty())
        assertEquals("https://site.com/icon.svg", p3.organizationLogo)

        val p4 = result.passwords[3]
        assertEquals("Fourth Service", p4.title)
        assertEquals("test.net", p4.domain)
        assertEquals(1, p4.additionalFields.size)
        assertEquals("Another note", p4.additionalFields[0].value)
        assertEquals(String.empty, p4.organizationLogo)
    }

    @Test
    fun parseCsvWithMixedValidAndInvalidRowsIncludingLogoUrl() {
        val csvContent = """
            name,url,username,password,note,logoUrl
            Valid Entry 1,example.com,userA,passA,noteA,https://valid.com/logo.png
            Invalid Entry 1,too,few,columns,
            Valid Entry 2,valid.net,userB,passB,noteB,
            Invalid Entry 2,,userC,,noteC,
            Invalid Entry 3,badurl,userD,passD,noteD,https://badurl.com/logo.png
            Valid Entry 3,sub.domain.com,userE,passE,noteE,https://sub.domain.com/logo.svg
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(3, result.passwords.size)
        assertEquals(3, result.failedEntries.size)
        assertEquals(7, result.summary.totalLinesProcessed)
        assertEquals(6, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)

        assertEquals(3, result.failedEntries[0].lineNumber)
        assertTrue(result.failedEntries[0].reason!!.contains("Invalid URL format for domain: 'too'."))

        assertEquals(5, result.failedEntries[1].lineNumber)
        assertTrue(result.failedEntries[1].reason!!.contains("Missing required field (url cannot be empty)."))

        assertEquals(6, result.failedEntries[2].lineNumber)
        assertTrue(result.failedEntries[2].reason!!.contains("Invalid URL format for domain: 'badurl'."))

        assertEquals("Valid Entry 1", result.passwords[0].title)
        assertEquals("https://valid.com/logo.png", result.passwords[0].organizationLogo)
    }

    @Test
    fun parseCsvWithInvalidDomainUrls() {
        val csvContent = """
            name,url,username,password,note,logoUrl
            Account 1,not-a-url,user1,pass1,note1,https://logo.com/logo.png
            Account 2,ftp://example.com,user2,pass2,note2,
            Account 3,valid.com,user3,pass3,note3,https://valid.com/logo.svg
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
        assertEquals(2, result.failedEntries.size)

        assertTrue(result.failedEntries[0].reason!!.contains("Invalid URL format for domain: 'not-a-url'."))
        assertTrue(result.failedEntries[1].reason!!.contains("Invalid URL format for domain: 'ftp://example.com'."))

        assertEquals("Account 3", result.passwords[0].title)
        assertEquals("https://valid.com/logo.svg", result.passwords[0].organizationLogo)
    }

    @Test
    fun parseCsvWithAdditionalFieldsAndLogoUrl() {
        val csvContent = """
            name,url,username,password,note,logoUrl,note1,note2
            Item 1,site.com,u1,p1,main notes,https://logo.com/item1.png,CustomField|Value1,AnotherField|Value2
            Item 2,site2.com,u2,p2,only main notes,,
            Item 3,site3.com,u3,p3,Note without pipe,https://logo.com/item3.png, Just a value
            Item 4,site4.com,u4,p4,,https://logo.com/item4.png,CustomField|Value3
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(4, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        val p1 = result.passwords[0]
        assertEquals(3, p1.additionalFields.size)
        assertEquals("Note", p1.additionalFields[0].title)
        assertEquals("main notes", p1.additionalFields[0].value)
        assertEquals("CustomField", p1.additionalFields[1].title)
        assertEquals("Value1", p1.additionalFields[1].value)
        assertEquals("AnotherField", p1.additionalFields[2].title)
        assertEquals("Value2", p1.additionalFields[2].value)
        assertEquals("https://logo.com/item1.png", p1.organizationLogo)

        val p2 = result.passwords[1]
        assertEquals(1, p2.additionalFields.size)
        assertEquals("Note", p2.additionalFields[0].title)
        assertEquals("only main notes", p2.additionalFields[0].value)
        assertEquals(String.empty, p2.organizationLogo)

        val p3 = result.passwords[2]
        assertEquals(2, p3.additionalFields.size)
        assertEquals("Note", p3.additionalFields[0].title)
        assertEquals("Note without pipe", p3.additionalFields[0].value)
        assertEquals("Note", p3.additionalFields[1].title)
        assertEquals("Just a value", p3.additionalFields[1].value)
        assertEquals("https://logo.com/item3.png", p3.organizationLogo)

        val p4 = result.passwords[3]
        assertEquals(1, p4.additionalFields.size)
        assertEquals("CustomField", p4.additionalFields[0].title)
        assertEquals("Value3", p4.additionalFields[0].value)
        assertEquals("https://logo.com/item4.png", p4.organizationLogo)
    }

    @Test
    fun parseCsvWithEmptyLinesBetweenData() {
        val csvContent = """
            name,url,username,password,note,logoUrl
            Entry1,example.com,user1,pass1,note1,https://logo.com/entry1.png

            Entry2,example.net,user2,pass2,note2,
            
            Entry3,example.org,user3,pass3,note3,https://logo.com/entry3.png
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(3, result.passwords.size)
        assertEquals(2, result.failedEntries.size)
        assertEquals(6, result.summary.totalLinesProcessed)
        assertEquals(5, result.summary.dataLinesProcessed)
        assertTrue(result.summary.headerValidated)

        assertEquals("Entry1", result.passwords[0].title)
        assertEquals("Entry2", result.passwords[1].title)
        assertEquals("Entry3", result.passwords[2].title)
        assertEquals("https://logo.com/entry1.png", result.passwords[0].organizationLogo)
        assertEquals(String.empty, result.passwords[1].organizationLogo)
        assertEquals("https://logo.com/entry3.png", result.passwords[2].organizationLogo)
    }

    @Test
    fun parseCsvWithWhitespaceInFieldsAndLogoUrl() {
        val csvContent = """
            name , url , username , password , note , logoUrl
             My Item 1  ,https://example.com,  user A  ,  pass B  ,  Notes C  ,  https://example.com/logo.png  
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        val p1 = result.passwords[0]
        assertEquals("My Item 1", p1.title)
        assertEquals("https://example.com", p1.domain)
        assertEquals("user A", p1.userName)
        assertEquals("pass B", p1.password)
        assertEquals("Notes C", p1.additionalFields[0].value)
        assertEquals("https://example.com/logo.png", p1.organizationLogo)
    }

    @Test
    fun parseCsvWithAdditionalFieldsContainingCommasOrPipesInValueWithLogoUrl() {
        val csvContent = """
            name,url,username,password,note,logoUrl,extra1
            Test Item,test.com,user,pass,Note with|comma,https://logo.com/test.png,Key|Value with|pipes
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
        assertEquals(0, result.failedEntries.size)

        val p1 = result.passwords[0]
        assertEquals("Note with", p1.additionalFields[0].title)
        assertEquals("comma", p1.additionalFields[0].value)

        assertEquals("Key", p1.additionalFields[1].title)
        assertEquals("Value with|pipes", p1.additionalFields[1].value)

        assertEquals("https://logo.com/test.png", p1.organizationLogo)
    }

    @Test
    fun parseCsvWithMissingLogoUrlColumn() {
        val csvContent = """
            name,url,username,password,note
            Item 1,site.com,u1,p1,main notes
        """.trimIndent()

        val result = CSVImportExportUtils.parseCsvContentToPasswords(csvContent)

        assertIs<CsvParsingResult.Success>(result)
        assertEquals(1, result.passwords.size)
    }
}