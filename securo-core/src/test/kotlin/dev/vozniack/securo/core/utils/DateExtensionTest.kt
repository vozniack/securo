package dev.vozniack.securo.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
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
    fun `map local date`() {
        val localDate: LocalDate = LocalDate.of(1990, 1, 1)
        val stringLocalDate = "1990-01-01"

        assertEquals(localDate, stringLocalDate.toLocalDate())
        assertEquals(stringLocalDate, localDate.toStringLocalDate())
    }

    @Test
    fun `map local datetime`() {
        val localDateTime: LocalDateTime = LocalDateTime.of(1990, 1, 1, 12, 0, 0)
        val stringLocalDate = "1990-01-01T12:00:00"

        assertEquals(stringLocalDate, localDateTime.toStringLocalDateTime())
    }
}
