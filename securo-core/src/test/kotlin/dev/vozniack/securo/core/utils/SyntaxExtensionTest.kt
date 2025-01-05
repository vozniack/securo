package dev.vozniack.securo.core.utils

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SyntaxExtensionTest {

    @Test
    fun `throw exception if condition true`() {
        assertThrows<Exception> {
            throwIfTrue(Exception("Some exception")) {
                true
            }
        }

        throwIfTrue(Exception("Some exception")) {
            false
        }
    }

    @Test
    fun `take string if not empty`() {
        assertNotNull("something here".takeIfNotEmpty())
        assertNull("".takeIfNotEmpty())
        assertNull(null.takeIfNotEmpty())
    }
}
