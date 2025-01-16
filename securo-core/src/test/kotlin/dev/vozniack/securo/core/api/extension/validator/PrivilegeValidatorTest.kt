package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreatePrivilegeRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdatePrivilegeRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class PrivilegeValidatorTest @Autowired constructor(
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
}
