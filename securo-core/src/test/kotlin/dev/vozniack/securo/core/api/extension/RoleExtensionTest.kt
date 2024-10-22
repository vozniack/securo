package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateRoleRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class RoleExtensionTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `validate create role request`() {
        val system: System = systemRepository.save(mockSystem())

        mockCreateRoleRequestDto(systemId = system.id).validate()

        assertThrows<BadRequestException> {
            mockCreateRoleRequestDto(name = "", systemId = system.id).validate()
        }

        assertThrows<BadRequestException> {
            mockCreateRoleRequestDto(code = "", systemId = system.id).validate()
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

    @Test
    fun `map create role request to role`() {
        val system: System = systemRepository.save(mockSystem())

        val request: CreateRoleRequestDto = mockCreateRoleRequestDto(systemId = system.id)
        val role: Role = request.toRole(system)

        assertEquals(request.name, role.name)
        assertEquals(request.code, role.code)
        assertEquals(request.description, role.description)
        assertEquals(request.systemId, role.system.id)
    }

    @Test
    fun `map role to dto`() {
        val system: System = systemRepository.save(mockSystem())

        val role: Role = mockRole(system = system)
        val roleDto: RoleDto = role.toDto()

        assertEquals(role.id, roleDto.id)
        assertEquals(role.scope, roleDto.scope)
        assertEquals(role.name, roleDto.name)
        assertEquals(role.code, roleDto.code)
        assertEquals(role.description, roleDto.description)
        assertEquals(role.active, roleDto.active)
    }
}
