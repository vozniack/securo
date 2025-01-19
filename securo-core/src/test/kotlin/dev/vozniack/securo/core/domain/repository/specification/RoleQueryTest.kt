package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockTeam
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RoleQueryTest @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val teamRepository: TeamRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        roleRepository.deleteAll()
        teamRepository.deleteAll()
    }

    @Test
    fun `role specification builder`() {
        val team: Team = teamRepository.save(mockTeam())
        val otherTeam: Team = teamRepository.save(mockTeam(name = "Other", code = "OTHR"))

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", team = team))
        roleRepository.save(mockRole(name = "Supporting Admin", code = "SUPADMIN", team = team))
        roleRepository.save(mockRole(name = "Supervisor", code = "SPRVS", team = team))
        roleRepository.save(mockRole(name = "User", code = "USER", team = otherTeam))

        var roles: List<Role> = roleRepository.findAll(RoleQuery().toSpecification())
        assertEquals(4, roles.size)

        roles = roleRepository.findAll(RoleQuery(name = "Admin").toSpecification())
        assertEquals(2, roles.size)

        roles = roleRepository.findAll(RoleQuery(code = "ADMIN").toSpecification())
        assertEquals(2, roles.size)

        roles = roleRepository.findAll(RoleQuery(teamId = otherTeam.id.toString()).toSpecification())
        assertEquals(1, roles.size)
    }
}
