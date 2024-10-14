package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockCreateRoleRequestDto
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdateRoleRequestDto
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

class RoleControllerTest @Autowired constructor(
    private val roleRepository: RoleRepository,
    private val systemRepository: SystemRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun `clean up`() {
        roleRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        val system: System = systemRepository.save(mockSystem())

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", system = system))
        roleRepository.save(mockRole(name = "User", code = "USER", system = system))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        mockMvc.perform(
            get("/api/v1/roles/page?systemId=${system.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `find all in list`() {
        val system: System = systemRepository.save(mockSystem())

        roleRepository.save(mockRole(name = "Admin", code = "ADMIN", system = system))
        roleRepository.save(mockRole(name = "User", code = "USER", system = system))

        val roles: List<RoleDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/roles/list?systemId=${system.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(2, roles.size)
    }

    @Test
    fun `get by id`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))

        val roleDto: RoleDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/roles/${role.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(role.id, roleDto.id)
    }

    @Test
    fun `create role`() {
        val system: System = systemRepository.save(mockSystem())
        val request: CreateRoleRequestDto = mockCreateRoleRequestDto(systemId = system.id)

        val roleDto: RoleDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isCreated).andReturn().response.contentAsString
        )

        assertEquals(1, roleRepository.count())
        assertNotNull(roleDto)
    }

    @Test
    fun `update role`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))
        val request: UpdateRoleRequestDto = mockUpdateRoleRequestDto()

        val roleDto: RoleDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/roles/${role.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(role.id, roleDto.id)
    }

    @Test
    fun `delete role`() {
        val system: System = systemRepository.save(mockSystem())
        val role: Role = roleRepository.save(mockRole(system = system))

        assertEquals(1, roleRepository.count())

        mockMvc.perform(
            delete("/api/v1/roles/${role.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, roleRepository.count())
    }
}
