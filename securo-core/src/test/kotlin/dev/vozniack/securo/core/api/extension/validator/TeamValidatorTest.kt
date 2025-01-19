package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateTeamRequestDto
import dev.vozniack.securo.core.mock.mockUpdateTeamRequestDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TeamValidatorTest {

    @Test
    fun `validate create team request`() {
        mockCreateTeamRequestDto().validate()

        assertThrows<BadRequestException> {
            mockCreateTeamRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockCreateTeamRequestDto(code = "").validate()
        }
    }

    @Test
    fun `validate update role request`() {
        mockUpdateTeamRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdateTeamRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdateTeamRequestDto(code = "").validate()
        }
    }
}
