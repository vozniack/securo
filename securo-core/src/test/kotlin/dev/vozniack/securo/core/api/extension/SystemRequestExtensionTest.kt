package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateSystemRequestDto
import dev.vozniack.securo.core.mock.mockUpdateSystemRequestDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SystemRequestExtensionTest {

    @Test
    fun `validate create system request`() {
        mockCreateSystemRequestDto().validate()

        assertThrows<BadRequestException> {
            mockCreateSystemRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockCreateSystemRequestDto(code = "").validate()
        }
    }

    @Test
    fun `validate update system request`() {
        mockUpdateSystemRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdateSystemRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdateSystemRequestDto(code = "").validate()
        }
    }
}
