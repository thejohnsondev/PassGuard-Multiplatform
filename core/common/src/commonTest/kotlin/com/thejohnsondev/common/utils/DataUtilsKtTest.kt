package com.thejohnsondev.common.utils

import kotlin.test.Test
import kotlin.test.assertEquals


class DataUtilsKtTest {

    @Test
    fun testHidden_withNonEmptyString() {
        val input = "HelloWorld"
        val expected = "**********"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withEmptyString() {
        val input = ""
        val expected = ""
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withSpaces() {
        val input = "Hello World"
        val expected = "***********"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withSpecialCharacters() {
        val input = "Hello@World!"
        val expected = "************"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testGetPrettyErrorMessage_withError() {
        val error = "Some error occurred"
        val result = getPrettyErrorMessage(error)
        assertEquals("Some error occurred", result)
    }

    @Test
    fun testGetPrettyErrorMessage_withNullError() {
        val error: String? = null
        val result = getPrettyErrorMessage(error)
        assertEquals("Unknown error", result)
    }


}