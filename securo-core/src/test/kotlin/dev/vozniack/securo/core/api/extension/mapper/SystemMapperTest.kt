package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.SystemDto
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockCreateSystemRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.utils.toStringLocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SystemMapperTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `map create system request to system`() {
        val parent: System = systemRepository.save(mockSystem())

        val request: CreateSystemRequestDto = mockCreateSystemRequestDto(name = "System Zero", code = "SYS0")
        val system: System = request.toSystem(parent)

        assertEquals(request.name, system.name)
        assertEquals(request.code, system.code)
        assertEquals(request.description, system.description)
        assertEquals(request.icon, system.icon)
        assertEquals(parent, system.parent)
        assertNotNull(system.createdAt)
        assertNull(system.updatedAt)

        val systemWithoutParent: System = request.toSystem(null)

        assertEquals(request.name, systemWithoutParent.name)
        assertEquals(request.code, systemWithoutParent.code)
        assertEquals(request.description, systemWithoutParent.description)
        assertEquals(request.icon, systemWithoutParent.icon)
        assertNull(systemWithoutParent.parent)
        assertNotNull(systemWithoutParent.createdAt)
        assertNull(systemWithoutParent.updatedAt)
    }

    @Test
    fun `map system to dto`() {
        val system: System = mockSystem()
        val systemDto: SystemDto = system.toDto()

        assertEquals(system.id, systemDto.id)
        assertEquals(system.scope, systemDto.scope)
        assertEquals(system.name, systemDto.name)
        assertEquals(system.code, systemDto.code)
        assertEquals(system.description, systemDto.description)
        assertEquals(system.icon, systemDto.icon)
        assertEquals(system.active, systemDto.active)
        assertEquals(system.createdAt.toStringLocalDateTime(), systemDto.createdAt)
        assertEquals(system.updatedAt?.toStringLocalDateTime(), systemDto.updatedAt)
    }
}
