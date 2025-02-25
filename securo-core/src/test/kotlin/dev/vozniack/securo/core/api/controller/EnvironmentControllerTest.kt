package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.EnvironmentDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentsOrderRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toDto
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.domain.entity.EnvironmentType
import dev.vozniack.securo.core.domain.repository.EnvironmentRepository
import dev.vozniack.securo.core.mock.mockCreateEnvironmentRequestDto
import dev.vozniack.securo.core.mock.mockEnvironment
import dev.vozniack.securo.core.mock.mockUpdateEnvironmentRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class EnvironmentControllerTest @Autowired constructor(
    private val environmentRepository: EnvironmentRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

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

        val environments: Map<EnvironmentType, List<EnvironmentDto>> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/environments/list")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

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

        val environmentDto: EnvironmentDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/environments/${environment.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(environment.id, environmentDto.id)
    }

    @Test
    fun `create environment`() {
        val request: CreateEnvironmentRequestDto = mockCreateEnvironmentRequestDto()

        val environmentDto: EnvironmentDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/environments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isCreated).andReturn().response.contentAsString
        )

        assertEquals(1, environmentRepository.count())
        assertNotNull(environmentDto)
    }

    @Test
    fun `update environment`() {
        val environment: Environment = environmentRepository.save(mockEnvironment())
        val request: UpdateEnvironmentRequestDto = mockUpdateEnvironmentRequestDto()

        val environmentDto: EnvironmentDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/environments/${environment.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(environment.id, environmentDto.id)
    }

    @Test
    fun `update environments order`() {
        val dev1 = environmentRepository.save(
            mockEnvironment(name = "Dev 1", code = "DEV1", type = EnvironmentType.DEV, index = 0)
        ).apply { index = 2 }

        val dev2 = environmentRepository.save(
            mockEnvironment(name = "Dev 2", code = "DEV2", type = EnvironmentType.DEV, index = 1)
        ).apply { index = 0 }

        val dev3 = environmentRepository.save(
            mockEnvironment(name = "Dev 3", code = "DEV3", type = EnvironmentType.DEV, index = 2)
        ).apply { index = 1 }

        val request = UpdateEnvironmentsOrderRequestDto(
            type = EnvironmentType.DEV,
            environments = listOf(dev2.toDto(), dev3.toDto(), dev1.toDto())
        )

        val environments: List<EnvironmentDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/environments/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(0, environments[0].index)
        assertEquals("DEV2", environments[0].code)

        assertEquals(1, environments[1].index)
        assertEquals("DEV3", environments[1].code)

        assertEquals(2, environments[2].index)
        assertEquals("DEV1", environments[2].code)
    }

    @Test
    fun `delete environment`() {
        val environment: Environment = environmentRepository.save(mockEnvironment())

        mockMvc.perform(
            delete("/api/v1/environments/${environment.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, environmentRepository.count())
    }
}
