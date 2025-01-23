package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.domain.entity.EnvironmentType
import dev.vozniack.securo.core.domain.repository.EnvironmentRepository
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateEnvironmentRequestDto
import dev.vozniack.securo.core.mock.mockEnvironment
import dev.vozniack.securo.core.mock.mockUpdateEnvironmentRequestDto
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class EnvironmentServiceTest @Autowired constructor(
    private val environmentService: EnvironmentService,
    private val environmentRepository: EnvironmentRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        environmentRepository.deleteAll()
    }

    @Test
    fun `find all`() {
        environmentRepository.saveAll(
            listOf(
                mockEnvironment(name = "Dev 1", code = "DEV1", type = EnvironmentType.DEV, index = 0),
                mockEnvironment(name = "Dev 2", code = "DEV2", type = EnvironmentType.DEV, index = 1),
                mockEnvironment(name = "Test", code = "TEST", type = EnvironmentType.TEST, index = 0),
                mockEnvironment(name = "Prod 2", code = "PROD2", type = EnvironmentType.PROD, index = 1),
                mockEnvironment(name = "Prod 1", code = "PROD1", type = EnvironmentType.PROD, index = 0)
            )
        )

        val environments: Map<EnvironmentType, List<Environment>> = environmentService.findAll()

        assertEquals(2, environments[EnvironmentType.DEV]?.size)
        assertEquals("DEV1", environments[EnvironmentType.DEV]?.get(0)?.code)
        assertEquals("DEV2", environments[EnvironmentType.DEV]?.get(1)?.code)

        assertEquals(1, environments[EnvironmentType.TEST]?.size)
        assertEquals("TEST", environments[EnvironmentType.TEST]?.get(0)?.code)

        assertEquals(2, environments[EnvironmentType.PROD]?.size)
        assertEquals("PROD1", environments[EnvironmentType.PROD]?.get(0)?.code)
        assertEquals("PROD2", environments[EnvironmentType.PROD]?.get(1)?.code)
    }

    @Test
    fun `get by id`() {
        val environment: Environment = environmentRepository.save(mockEnvironment())
        val fetchedEnvironment: Environment = environmentService.getById(environment.id)

        assertEquals(environment.id, fetchedEnvironment.id)
    }

    @Test
    fun `get not existing environment by id`() {
        assertThrows<NotFoundException> {
            environmentService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `create environment`() {
        val request: CreateEnvironmentRequestDto = mockCreateEnvironmentRequestDto()
        val environment: Environment = environmentService.create(request)

        assertEquals(1, environmentRepository.count())

        assertEquals(request.name, environment.name)
        assertEquals(request.code, environment.code)
        assertEquals(request.type, environment.type)
        assertEquals(request.description, environment.description)
        assertEquals(0, environment.index)
        assertNotNull(environment.createdAt)
        assertNull(environment.updatedAt)
    }

    @Test
    fun `create environment with already existing code and type`() {
        environmentRepository.save(mockEnvironment(code = "B2B", type = EnvironmentType.DEV))

        // Can't create environment with the same code and type
        assertThrows<ConflictException> {
            environmentService.create(mockCreateEnvironmentRequestDto(code = "B2B", type = EnvironmentType.DEV))
        }

        // But different set of code and type is allowed
        environmentService.create(mockCreateEnvironmentRequestDto(code = "B2B", type = EnvironmentType.STAGE))
    }

    @Test
    fun `update environment`() {
        val environment: Environment = environmentRepository.save(mockEnvironment())

        val request: UpdateEnvironmentRequestDto = mockUpdateEnvironmentRequestDto()
        val updatedEnvironment: Environment = environmentService.update(environment.id, request)

        assertEquals(request.name, updatedEnvironment.name)
        assertEquals(request.code, updatedEnvironment.code)
        assertEquals(request.type, updatedEnvironment.type)
        assertEquals(request.description, updatedEnvironment.description)
        assertEquals(1, updatedEnvironment.index)
        assertNotNull(updatedEnvironment.createdAt)
        assertNotNull(updatedEnvironment.updatedAt)
    }

    @Test
    fun `update environment with already existing code`() {
        val prodEnvironment: Environment = environmentRepository.save(
            mockEnvironment(name = "Prod", code = "PROD", type = EnvironmentType.PROD)
        )

        val stageEnvironment: Environment = environmentRepository.save(
            mockEnvironment(name = "Stage", code = "STAGE", type = EnvironmentType.STAGE)
        )

        val request: UpdateEnvironmentRequestDto = mockUpdateEnvironmentRequestDto(name = "Stage 2", code = "STAGE")

        // allowed because of changing the same environment
        environmentService.update(stageEnvironment.id, request)

        // not allowed because of already used environment code
        assertThrows<ConflictException> {
            environmentService.update(prodEnvironment.id, request)
        }
    }

    @Test
    fun `delete environment`() {
        val environment: Environment = environmentRepository.save(mockEnvironment())

        assertEquals(1, environmentRepository.count())

        environmentService.delete(environment.id)

        assertEquals(0, environmentRepository.count())
    }
}
