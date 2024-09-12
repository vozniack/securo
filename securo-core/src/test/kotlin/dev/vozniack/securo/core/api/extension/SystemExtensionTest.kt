package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.SystemDto
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateSystemRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateSystemRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class SystemExtensionTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `validate create system request`() {
        mockCreateSystemRequestDto().validate()

        assertThrows<BadRequestException> {
            mockCreateSystemRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockCreateSystemRequestDto(code = "").validate()
        }
    }

    @Test
    fun `validate update system request`() {
        mockUpdateSystemRequestDto().validate()

        assertThrows<BadRequestException> {
            mockUpdateSystemRequestDto(name = "").validate()
        }

        assertThrows<BadRequestException> {
            mockUpdateSystemRequestDto(code = "").validate()
        }
    }

    @Test
    fun `map create system request to system`() {
        val parent: System = systemRepository.save(mockSystem())

        val request: CreateSystemRequestDto = mockCreateSystemRequestDto(name = "System Zero", code = "SYS0")
        val system: System = request.toSystem(parent)

        assertEquals(request.name, system.name)
        assertEquals(request.code, system.code)
        assertEquals(request.description, system.description)
        assertEquals(parent, system.parent)

        val systemWithoutParent: System = request.toSystem(null)

        assertEquals(request.name, systemWithoutParent.name)
        assertEquals(request.code, systemWithoutParent.code)
        assertEquals(request.description, systemWithoutParent.description)
        assertNull(systemWithoutParent.parent)
    }

    @Test
    fun `map system to dto`() {
        val system: System = mockSystem()
        val dto: SystemDto = system.toDto()

        assertEquals(system.id, dto.id)
        assertEquals(system.scope, dto.scope)
        assertEquals(system.name, dto.name)
        assertEquals(system.code, dto.code)
        assertEquals(system.description, dto.description)
        assertEquals(system.active, dto.active)
    }
}
