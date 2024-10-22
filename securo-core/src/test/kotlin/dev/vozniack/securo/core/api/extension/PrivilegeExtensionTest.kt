package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.PrivilegeDto
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreatePrivilegeRequestDto
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdatePrivilegeRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class PrivilegeExtensionTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `validate create privilege request`() {
        val system: System = systemRepository.save(mockSystem())

        mockCreatePrivilegeRequestDto(systemId = system.id).validate()

        assertThrows<BadRequestException> {
            mockCreatePrivilegeRequestDto(name = "", systemId = system.id).validate()
        }

        assertThrows<BadRequestException> {
            mockCreatePrivilegeRequestDto(code = "", systemId = system.id).validate()
        }
    }

    @Test
    fun `validate update role request`() {
        mockUpdatePrivilegeRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdatePrivilegeRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdatePrivilegeRequestDto(code = "").validate()
        }
    }

    @Test
    fun `map create privilege request to privilege`() {
        val system: System = systemRepository.save(mockSystem())

        val request: CreatePrivilegeRequestDto = mockCreatePrivilegeRequestDto(systemId = system.id)
        val privilege: Privilege = request.toPrivilege(system)

        assertEquals(request.name, privilege.name)
        assertEquals(request.code, privilege.code)
        assertEquals(request.description, privilege.description)
        assertEquals(request.index, privilege.index)
        assertEquals(request.systemId, privilege.system.id)
        assertEquals(request.parentId, privilege.parent?.id)
    }

    @Test
    fun `map privilege to dto`() {
        val system: System = systemRepository.save(mockSystem())

        val privilege: Privilege = mockPrivilege(system = system)
        val privilegeDto: PrivilegeDto = privilege.toDto()

        assertEquals(privilege.id, privilegeDto.id)
        assertEquals(privilege.scope, privilegeDto.scope)
        assertEquals(privilege.name, privilegeDto.name)
        assertEquals(privilege.code, privilegeDto.code)
        assertEquals(privilege.description, privilegeDto.description)
        assertEquals(privilege.index, privilegeDto.index)
        assertEquals(privilege.system.id, privilegeDto.systemId)
        assertEquals(privilege.parent?.id, privilegeDto.parentId)
    }
}
