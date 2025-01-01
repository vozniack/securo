package dev.vozniack.securo.core.utils

import java.time.LocalDate
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DateExtensionTest {

    @Test
    fun `check if date is correct`() {
        assertTrue("1990-01-01".isCorrectDate())

        assertFalse("01-01-1990".isCorrectDate())
        assertFalse("1990-13-01".isCorrectDate())
    }

    @Test
    fun `map date`() {
        val localDate: LocalDate = LocalDate.of(1990, 1,1)
        val stringLocalDate = "1990-01-01"

        assertEquals(localDate, stringLocalDate.toLocalDate())
        assertEquals(stringLocalDate.toLocalDate(), localDate)
    }
}
