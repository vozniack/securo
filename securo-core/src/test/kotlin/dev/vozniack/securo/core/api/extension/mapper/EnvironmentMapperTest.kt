package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.EnvironmentDto
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.mock.mockCreateEnvironmentRequestDto
import dev.vozniack.securo.core.mock.mockEnvironment
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class EnvironmentMapperTest {

    @Test
    fun `map create environment request to environment`() {
        val request: CreateEnvironmentRequestDto = mockCreateEnvironmentRequestDto()
        val environment: Environment = request.toEnvironment(1)

        assertEquals(request.name, environment.name)
        assertEquals(request.code, environment.code)
        assertEquals(request.type, environment.type)
        assertEquals(request.description, environment.description)
        assertEquals(1, environment.index)
    }

    @Test
    fun `map environment to dto`() {
        val environment: Environment = mockEnvironment().apply {
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        }

        val dto: EnvironmentDto = environment.toDto()

        assertEquals(environment.name, dto.name)
        assertEquals(environment.code, dto.code)
        assertEquals(environment.type, dto.type)
        assertEquals(environment.description, dto.description)
        assertEquals(environment.index, dto.index)
        assertNotNull(dto.createdAt)
        assertNotNull(dto.updatedAt)
    }
}
