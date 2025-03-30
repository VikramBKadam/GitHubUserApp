package com.example.androidproject

import com.example.androidproject.util.Utils
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals


@RunWith(JUnit4::class)
class UtilsTest {

    @Test
    fun `formatDateTime with valid ISO string returns formatted date`() {
        val input = "2023-08-30T14:30:00Z"
        val expectedOutput = "August 30, 2023 | 02:30 PM"
        val result = Utils.formatDateTime(input)
        assertEquals(result, expectedOutput)
    }

    @Test
    fun `formatDateTime with invalid ISO string returns N_A`() {
        val input = "invalid-date"
        val result = Utils.formatDateTime(input)
        assertEquals(result, "N/A")
    }

    @Test
    fun `formatDateTime with empty string returns N_A`() {
        val input = ""
        val result = Utils.formatDateTime(input)
        assertEquals(result, "N/A")
    }

    @Test
    fun `formatDateTime with null string returns N_A`() {
        val input: String? = null
        val result = Utils.formatDateTime(input ?: "")
        assertEquals(result, "N/A")
    }
}