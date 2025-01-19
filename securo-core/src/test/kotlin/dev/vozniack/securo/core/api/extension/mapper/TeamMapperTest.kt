package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.TeamDto
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.mock.mockCreateTeamRequestDto
import dev.vozniack.securo.core.mock.mockTeam
import dev.vozniack.securo.core.utils.toStringLocalDateTime
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TeamMapperTest {

    @Test
    fun `map create team request to team`() {
        val request: CreateTeamRequestDto = mockCreateTeamRequestDto()
        val team: Team = request.toTeam()

        assertEquals(request.name, team.name)
        assertEquals(request.code, team.code)
        assertEquals(request.description, team.description)
        assertNotNull(team.createdAt)
        assertNull(team.updatedAt)
    }

    @Test
    fun `map team to dto`() {
        val team: Team = mockTeam().apply { updatedAt = LocalDateTime.now() }
        val teamDto: TeamDto = team.toDto()

        assertEquals(team.id, teamDto.id)
        assertEquals(team.scope, teamDto.scope)
        assertEquals(team.name, teamDto.name)
        assertEquals(team.code, teamDto.code)
        assertEquals(team.description, teamDto.description)
        assertEquals(team.createdAt.toStringLocalDateTime(), teamDto.createdAt)
        assertEquals(team.updatedAt?.toStringLocalDateTime(), teamDto.updatedAt)
    }
}
