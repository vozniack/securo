package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateEnvironmentRequestDto
import dev.vozniack.securo.core.mock.mockUpdateEnvironmentRequestDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EnvironmentValidatorTest {

    @Test
    fun `validate create environment request`() {
        mockCreateEnvironmentRequestDto().validate()

        assertThrows<BadRequestException> {
            mockCreateEnvironmentRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockCreateEnvironmentRequestDto(code = "").validate()
        }
    }

    @Test
    fun `validate update environment request`() {
        mockUpdateEnvironmentRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdateEnvironmentRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdateEnvironmentRequestDto(code = "").validate()
        }
    }
}
