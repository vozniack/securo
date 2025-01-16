package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockTeam
import dev.vozniack.securo.core.utils.toStringLocalDateTime
import java.time.LocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RoleMapperTest @Autowired constructor(
    private val teamRepository: TeamRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        teamRepository.deleteAll()
    }

    @Test
    fun `map create role request to role`() {
        val team: Team = teamRepository.save(mockTeam())

        val request: CreateRoleRequestDto = mockCreateRoleRequestDto(teamId = team.id)
        val role: Role = request.toRole(team)

        assertEquals(request.name, role.name)
        assertEquals(request.code, role.code)
        assertEquals(request.description, role.description)
        assertEquals(request.teamId, role.team.id)
        assertNotNull(role.createdAt)
        assertNull(role.updatedAt)
    }

    @Test
    fun `map role to dto`() {
        val team: Team = teamRepository.save(mockTeam())

        val role: Role = mockRole(team = team).apply { updatedAt = LocalDateTime.now() }
        val roleDto: RoleDto = role.toDto()

        assertEquals(role.id, roleDto.id)
        assertEquals(role.scope, roleDto.scope)
        assertEquals(role.name, roleDto.name)
        assertEquals(role.code, roleDto.code)
        assertEquals(role.description, roleDto.description)
        assertEquals(role.active, roleDto.active)
        assertEquals(role.createdAt.toStringLocalDateTime(), roleDto.createdAt)
        assertEquals(role.updatedAt?.toStringLocalDateTime(), roleDto.updatedAt)
    }
}
