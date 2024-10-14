package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateRoleRequestDto
import org.junit.jupiter.api.AfterEach
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
}
