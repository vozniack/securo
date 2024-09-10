package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.api.dto.entity.SystemDto
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockCreateSystemRequestDto
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateSystemRequestDto
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

class SystemControllerTest @Autowired constructor(
    private val systemRepository: SystemRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        systemRepository.save(mockSystem(name = "Sys1", code = "SYS1"))
        systemRepository.save(mockSystem(name = "Sys2", code = "SYS2"))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        mockMvc.perform(
            get("/api/v1/systems/page")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `find all in list`() {
        systemRepository.save(mockSystem(name = "Sys1", code = "SYS1"))
        systemRepository.save(mockSystem(name = "Sys2", code = "SYS2"))

        val systems: List<SystemDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/systems/list")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(2, systems.size)
    }

    @Test
    fun `get by id`() {
        val system: System = systemRepository.save(mockSystem())

        val systemDto: SystemDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/systems/${system.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(system.id, systemDto.id)
    }

    @Test
    fun `create system`() {
        val request: CreateSystemRequestDto = mockCreateSystemRequestDto()

        val systemDto: SystemDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/systems")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isCreated).andReturn().response.contentAsString
        )

        assertEquals(1, systemRepository.count())
        assertNotNull(systemDto)
    }

    @Test
    fun `update system`() {
        val system: System = systemRepository.save(mockSystem())
        val request: UpdateSystemRequestDto = mockUpdateSystemRequestDto()

        val systemDto: SystemDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/systems/${system.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(system.id, systemDto.id)
    }

    @Test
    fun `delete system`() {
        val system: System = systemRepository.save(mockSystem())

        mockMvc.perform(
            delete("/api/v1/systems/${system.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, systemRepository.count())
    }
}
