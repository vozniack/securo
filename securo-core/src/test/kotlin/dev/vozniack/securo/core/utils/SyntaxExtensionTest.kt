package dev.vozniack.securo.core.utils

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
}
