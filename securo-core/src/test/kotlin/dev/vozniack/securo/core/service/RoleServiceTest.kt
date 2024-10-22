package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.specification.RoleQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateRoleRequestDto
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class RoleServiceTest @Autowired constructor(
    private val roleService: RoleService,
    private val roleRepository: RoleRepository,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        roleRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        val system: System = systemRepository.save(mockSystem())

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", system = system))
        roleRepository.save(mockRole(name = "User", code = "USER", system = system))

        val roles: Page<Role> = roleService.findAll(RoleQuery(), PageRequest.of(0, 24))

        assertEquals(2, roles.content.size)
    }

    @Test
    fun `find all in list`() {
        val system: System = systemRepository.save(mockSystem())

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", system = system))
        roleRepository.save(mockRole(name = "User", code = "USER", system = system))

        val roles: List<Role> = roleService.findAll(RoleQuery())

        assertEquals(2, roles.size)
    }

    @Test
    fun `get role by id`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))
        val fetchedRole: Role = roleService.getById(role.id)

        assertEquals(role.id, fetchedRole.id)
    }

    @Test
    fun `get not existing role by id`() {
        assertThrows<NotFoundException> {
            roleService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `create role`() {
        val system: System = systemRepository.save(mockSystem())
        val request: CreateRoleRequestDto = mockCreateRoleRequestDto(systemId = system.id)

        val role: Role = roleService.create(request)

        assertEquals(1, roleRepository.count())

        assertEquals(role.name, request.name)
        assertEquals(role.code, request.code)
        assertEquals(role.description, request.description)
        assertTrue(role.active)
    }

    @Test
    fun `create role with already existing code within system`() {
        val system: System = systemRepository.save(mockSystem())

        roleRepository.save(mockRole(system = system))

        assertThrows<ConflictException> {
            roleService.create(mockCreateRoleRequestDto(systemId = system.id))
        }
    }

    @Test
    fun `update role`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))

        val request: UpdateRoleRequestDto = mockUpdateRoleRequestDto()
        val updatedRole: Role = roleService.update(role.id, request)

        assertEquals(role.id, updatedRole.id)
        assertEquals(request.name, updatedRole.name)
        assertEquals(request.code, updatedRole.code)
        assertEquals(request.description, updatedRole.description)
    }

    @Test
    fun `update role with already existing code within system`() {
        val system: System = systemRepository.save(mockSystem())

        val role: Role = roleRepository.save(mockRole(system = system))
        val otherRole: Role = roleRepository.save(mockRole(name = "User", code = "USER", system = system))

        val request: UpdateRoleRequestDto = mockUpdateRoleRequestDto(code = "ADMIN")

        // allowed because of changing the same role
        roleService.update(role.id, request)

        // not allowed because of already used role code
        assertThrows<ConflictException> {
            roleService.update(otherRole.id, request)
        }
    }

    @Test
    fun `delete role`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))

        assertEquals(1, roleRepository.count())

        roleService.delete(role.id)

        assertEquals(0, roleRepository.count())
    }

    @Test
    fun `delete internal role`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(scope = ScopeType.INTERNAL, system = system))

        assertThrows<ForbiddenException> {
            roleService.delete(role.id)
        }
    }
}
