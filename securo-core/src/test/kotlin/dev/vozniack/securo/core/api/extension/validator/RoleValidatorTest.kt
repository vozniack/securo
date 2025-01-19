package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockTeam
import dev.vozniack.securo.core.mock.mockUpdateRoleRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class RoleValidatorTest @Autowired constructor(
    private val teamRepository: TeamRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        teamRepository.deleteAll()
    }

    @Test
    fun `validate create role request`() {
        val team: Team = teamRepository.save(mockTeam())

        mockCreateRoleRequestDto(teamId = team.id).validate()

        assertThrows<BadRequestException> {
            mockCreateRoleRequestDto(name = "", teamId = team.id).validate()
        }

        assertThrows<BadRequestException> {
            mockCreateRoleRequestDto(code = "", teamId = team.id).validate()
        }
    }

    @Test
    fun `validate update role request`() {
        mockUpdateRoleRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdateRoleRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdateRoleRequestDto(code = "").validate()
        }
    }
}
