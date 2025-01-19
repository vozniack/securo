package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.PrivilegeDto
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockCreatePrivilegeRequestDto
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.utils.toStringLocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class PrivilegeMapperTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

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
        assertNotNull(privilege.createdAt)
        assertNull(privilege.updatedAt)
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
        assertEquals(privilege.createdAt.toStringLocalDateTime(), privilegeDto.createdAt)
        assertEquals(privilege.updatedAt?.toStringLocalDateTime(), privilegeDto.updatedAt)
    }
}
