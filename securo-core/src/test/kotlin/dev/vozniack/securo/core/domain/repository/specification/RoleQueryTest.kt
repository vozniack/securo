package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockSystem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RoleQueryTest @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        roleRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `role specification builder`() {
        val system: System = systemRepository.save(mockSystem())
        val externalSystem: System = systemRepository.save(mockSystem(name = "External system", code = "EXT"))

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", system = system))
        roleRepository.save(mockRole(name = "Supporting Admin", code = "SUPADMIN", system = system))
        roleRepository.save(mockRole(name = "Supervisor", code = "SPRVS", system = system))
        roleRepository.save(mockRole(name = "User", code = "USER", system = system))
        roleRepository.save(mockRole(name = "External user", code = "EXT", system = externalSystem))

        var roles: List<Role> = roleRepository.findAll(RoleQuery().toSpecification())
        assertEquals(5, roles.size)

        roles = roleRepository.findAll(RoleQuery(name = "Admin").toSpecification())
        assertEquals(2, roles.size)

        roles = roleRepository.findAll(RoleQuery(code = "ADMIN").toSpecification())
        assertEquals(2, roles.size)

        roles = roleRepository.findAll(RoleQuery(systemId = externalSystem.id.toString()).toSpecification())
        assertEquals(1, roles.size)
    }
}
