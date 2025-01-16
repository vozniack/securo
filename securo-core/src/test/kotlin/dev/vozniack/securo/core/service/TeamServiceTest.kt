package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.UpdateTeamRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.domain.repository.specification.TeamQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateTeamRequestDto
import dev.vozniack.securo.core.mock.mockTeam
import dev.vozniack.securo.core.mock.mockUpdateTeamRequestDto
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class TeamServiceTest @Autowired constructor(
    private val teamService: TeamService,
    private val teamRepository: TeamRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        teamRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        teamRepository.save(mockTeam(name = "Securo Internal", code = "SEC"))
        teamRepository.save(mockTeam(name = "Securo External", code = "SEC_EXT"))


        val teams: Page<Team> = teamService.findAll(TeamQuery(), PageRequest.of(0, 24))

        assertEquals(2, teams.content.size)
    }

    @Test
    fun `find all in list`() {
        teamRepository.save(mockTeam(name = "Securo Internal", code = "SEC"))
        teamRepository.save(mockTeam(name = "Securo External", code = "SEC_EXT"))

        val teams: List<Team> = teamService.findAll(TeamQuery())

        assertEquals(2, teams.size)
    }

    @Test
    fun `get team by id`() {
        val team: Team = teamRepository.save(mockTeam())
        val fetchedTeam: Team = teamService.getById(team.id)

        assertEquals(team.id, fetchedTeam.id)
    }

    @Test
    fun `get not existing role by id`() {
        assertThrows<NotFoundException> {
            teamService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `find team by id`() {
        val team: Team = teamRepository.save(mockTeam())
        val fetchedTeam: Team? = teamService.findById(team.id)

        assertEquals(team.id, fetchedTeam?.id)
    }

    @Test
    fun `find not existing team by id`() {
        val fetchedTeam: Team? = teamService.findById(UUID.randomUUID())

        assertNull(fetchedTeam)
    }

    @Test
    fun `create team`() {
        val request: CreateTeamRequestDto = mockCreateTeamRequestDto()
        val team: Team = teamService.create(request)

        assertEquals(1, teamRepository.count())

        assertEquals(request.name, team.name)
        assertEquals(request.code, team.code)
        assertEquals(request.description, team.description)
        assertNotNull(team.createdAt)
        assertNull(team.updatedAt)
    }

    @Test
    fun `create team with already existing code`() {
        teamRepository.save(mockTeam())

        assertThrows<ConflictException> {
            teamService.create(mockCreateTeamRequestDto())
        }
    }

    @Test
    fun `update team`() {
        val team: Team = teamRepository.save(mockTeam())

        val request: UpdateTeamRequestDto = mockUpdateTeamRequestDto()
        val updatedTeam: Team = teamService.update(team.id, request)

        assertEquals(team.id, updatedTeam.id)
        assertEquals(request.name, updatedTeam.name)
        assertEquals(request.code, updatedTeam.code)
        assertEquals(request.description, updatedTeam.description)
        assertNotNull(updatedTeam.createdAt)
        assertNotNull(updatedTeam.updatedAt)
    }

    @Test
    fun `update system with already existing code`() {
        val team: Team = teamRepository.save(mockTeam())
        val teamExternal: Team = teamRepository.save(mockTeam(code = "EXT"))

        val request: UpdateTeamRequestDto = mockUpdateTeamRequestDto(code = "SEC")

        // allowed because of changing the same system
        teamService.update(team.id, request)

        // not allowed because of already used system code
        assertThrows<ConflictException> {
            teamService.update(teamExternal.id, request)
        }
    }

    @Test
    fun `delete team`() {
        val team: Team = teamRepository.save(mockTeam())

        assertEquals(1, teamRepository.count())

        teamService.delete(team.id)

        assertEquals(0, teamRepository.count())
    }

    @Test
    fun `delete internal team`() {
        val system: Team = teamRepository.save(mockTeam(scope = ScopeType.INTERNAL))

        assertThrows<ForbiddenException> {
            teamService.delete(system.id)
        }
    }
}
