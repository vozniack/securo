package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockLoginRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthRequestExtensionTest {

    @Test
    fun `validate login request`() {
        mockLoginRequest().validate()

        // missing email
        assertThrows<BadRequestException> {
            mockLoginRequest(email = "").validate()
        }

        // missing password
        assertThrows<BadRequestException> {
            mockLoginRequest(password = "").validate()
        }
    }
}
