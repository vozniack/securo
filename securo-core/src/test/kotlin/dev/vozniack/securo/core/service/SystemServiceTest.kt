package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.specification.SystemQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateSystemRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateSystemRequestDto
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class SystemServiceTest @Autowired constructor(
    private val systemService: SystemService,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        systemRepository.save(mockSystem(name = "Sys1", code = "SYS1"))
        systemRepository.save(mockSystem(name = "Sys2", code = "SYS2"))

        val systems: Page<System> = systemService.findAll(SystemQuery(), PageRequest.of(0, 24))

        assertEquals(2, systems.content.size)
    }

    @Test
    fun `find all in list`() {
        systemRepository.save(mockSystem(name = "Sys1", code = "SYS1"))
        systemRepository.save(mockSystem(name = "Sys2", code = "SYS2"))

        val systems: List<System> = systemService.findAll(SystemQuery())

        assertEquals(2, systems.size)
    }

    @Test
    fun `get system by id`() {
        val system: System = systemRepository.save(mockSystem())
        val fetchedSystem: System = systemService.getById(system.id)

        assertEquals(system.id, fetchedSystem.id)
    }

    @Test
    fun `get not existing system by id`() {
        assertThrows<NotFoundException> {
            systemService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `find system by id`() {
        val system: System = systemRepository.save(mockSystem())
        val fetchedSystem: System? = systemService.findById(system.id)

        assertEquals(system.id, fetchedSystem?.id)
    }

    @Test
    fun `find not existing system by id`() {
        val fetchedSystem: System? = systemService.findById(UUID.randomUUID())

        assertNull(fetchedSystem)
    }

    @Test
    fun `create system`() {
        val request: CreateSystemRequestDto = mockCreateSystemRequestDto()
        val system: System = systemService.create(request)

        assertEquals(1, systemRepository.count())

        assertEquals(request.name, system.name)
        assertEquals(request.code, system.code)
        assertEquals(request.description, system.description)
        assertTrue(system.active)
    }

    @Test
    fun `create system with already existing code`() {
        systemRepository.save(mockSystem())

        assertThrows<ConflictException> {
            systemService.create(mockCreateSystemRequestDto())
        }
    }

    @Test
    fun `update system`() {
        val system: System = systemRepository.save(mockSystem())

        val request: UpdateSystemRequestDto = mockUpdateSystemRequestDto()
        val updatedSystem: System = systemService.update(system.id, request)

        assertEquals(system.id, updatedSystem.id)
        assertEquals(request.name, updatedSystem.name)
        assertEquals(request.code, updatedSystem.code)
        assertEquals(request.description, updatedSystem.description)
    }

    @Test
    fun `update system with already existing code`() {
        val system: System = systemRepository.save(mockSystem())
        val systemZero: System = systemRepository.save(mockSystem(code = "SYS0"))

        val request: UpdateSystemRequestDto = mockUpdateSystemRequestDto(code = "SYS")

        // allowed because of code change within system
        systemService.update(system.id, request)

        // not allowed because of already used system code
        assertThrows<ConflictException> {
            systemService.update(systemZero.id, request)
        }
    }

    @Test
    fun `delete system`() {
        val system: System = systemRepository.save(mockSystem())

        assertEquals(1, systemRepository.count())

        systemService.delete(system.id)

        assertEquals(0, systemRepository.count())
    }

    @Test
    fun `delete internal system`() {
        val system: System = systemRepository.save(mockSystem(scope = ScopeType.INTERNAL))

        assertThrows<ForbiddenException> {
            systemService.delete(system.id)
        }
    }
}
