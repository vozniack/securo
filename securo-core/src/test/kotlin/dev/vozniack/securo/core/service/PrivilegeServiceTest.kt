package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.UpdatePrivilegeRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.PrivilegeRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.specification.PrivilegeQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreatePrivilegeRequestDto
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdatePrivilegeRequestDto
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class PrivilegeServiceTest @Autowired constructor(
    private val privilegeService: PrivilegeService,
    private val privilegeRepository: PrivilegeRepository,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        privilegeRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        val system: System = systemRepository.save(mockSystem())

        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))
        privilegeRepository.save(mockPrivilege(name = "Signup", code = "SIGNUP", system = system))

        val roles: Page<Privilege> = privilegeService.findAll(PrivilegeQuery(), PageRequest.of(0, 24))

        assertEquals(2, roles.content.size)
    }

    @Test
    fun `find all in list`() {
        val system: System = systemRepository.save(mockSystem())

        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))
        privilegeRepository.save(mockPrivilege(name = "Signup", code = "SIGNUP", system = system))

        val roles: List<Privilege> = privilegeService.findAll(PrivilegeQuery())

        assertEquals(2, roles.size)
    }

    @Test
    fun `get privilege by id`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))
        val fetchedPrivilege: Privilege = privilegeService.getById(privilege.id)

        assertEquals(privilege.id, fetchedPrivilege.id)
    }

    @Test
    fun `get not existing privilege by id`() {
        assertThrows<NotFoundException> {
            privilegeService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `find privilege by id`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))
        val fetchedPrivilege: Privilege? = privilegeService.findById(privilege.id)

        assertEquals(privilege.id, fetchedPrivilege?.id)
    }

    @Test
    fun `find not existing privilege by id`() {
        val fetchedPrivilege: Privilege? = privilegeService.findById(UUID.randomUUID())

        assertNull(fetchedPrivilege)
    }

    @Test
    fun `create privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val request: CreatePrivilegeRequestDto = mockCreatePrivilegeRequestDto(systemId = system.id)

        val privilege: Privilege = privilegeService.create(request)

        assertEquals(1, privilegeRepository.count())

        assertEquals(privilege.name, request.name)
        assertEquals(privilege.code, request.code)
        assertEquals(privilege.description, request.description)
    }

    @Test
    fun `create child privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val parentPrivilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        val request: CreatePrivilegeRequestDto = mockCreatePrivilegeRequestDto(
            name = "Subsystem login",
            code = "SUBLOGIN",
            systemId = system.id,
            parentId = parentPrivilege.id
        )

        val childPrivilege: Privilege = privilegeService.create(request)

        assertEquals(2, privilegeRepository.count())

        assertEquals(childPrivilege.name, request.name)
        assertEquals(childPrivilege.code, request.code)
        assertEquals(childPrivilege.description, request.description)
        assertEquals(childPrivilege.parent?.id, parentPrivilege.id)
    }

    @Test
    fun `create privilege with already existing code within system`() {
        val system: System = systemRepository.save(mockSystem())

        privilegeRepository.save(mockPrivilege(system = system))

        assertThrows<ConflictException> {
            privilegeService.create(mockCreatePrivilegeRequestDto(systemId = system.id))
        }
    }

    @Test
    fun `update privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        val request: UpdatePrivilegeRequestDto = mockUpdatePrivilegeRequestDto()
        val updatedPrivilege: Privilege = privilegeService.update(privilege.id, request)

        assertEquals(privilege.id, updatedPrivilege.id)
        assertEquals(request.name, updatedPrivilege.name)
        assertEquals(request.code, updatedPrivilege.code)
        assertEquals(request.description, updatedPrivilege.description)
    }

    @Test
    fun `update privilege with already existing code within system`() {
        val system: System = systemRepository.save(mockSystem())

        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))
        val otherPrivilege: Privilege = privilegeRepository.save(
            mockPrivilege(name = "User", code = "USER", system = system)
        )

        val request: UpdatePrivilegeRequestDto = mockUpdatePrivilegeRequestDto(code = "ADMIN")

        // allowed because of changing the same privilege
        privilegeService.update(privilege.id, request)

        // not allowed because of already used privilege code
        assertThrows<ConflictException> {
            privilegeService.update(otherPrivilege.id, request)
        }
    }

    @Test
    fun `delete privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        assertEquals(1, privilegeRepository.count())

        privilegeService.delete(privilege.id)

        assertEquals(0, privilegeRepository.count())
    }

    @Test
    fun `delete parent privilege`() {
        val system: System = systemRepository.save(mockSystem())

        val parentPrivilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        privilegeRepository.save(
            mockPrivilege(name = "Subsystem login", code = "SUBLOGIN", system = system, parent = parentPrivilege)
        )

        assertEquals(2, privilegeRepository.count())

        privilegeService.delete(parentPrivilege.id)

        assertEquals(0, privilegeRepository.count())
    }

    @Test
    fun `delete internal privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(scope = ScopeType.INTERNAL, system = system))

        assertThrows<ForbiddenException> {
            privilegeService.delete(privilege.id)
        }
    }
}
