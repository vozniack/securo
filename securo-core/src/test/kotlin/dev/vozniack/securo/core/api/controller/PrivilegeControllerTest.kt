package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.PrivilegeDto
import dev.vozniack.securo.core.api.dto.UpdatePrivilegeRequestDto
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.PrivilegeRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockCreatePrivilegeRequestDto
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUpdatePrivilegeRequestDto
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

class PrivilegeControllerTest @Autowired constructor(
    private val privilegeRepository: PrivilegeRepository,
    private val systemRepository: SystemRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun `clean up`() {
        privilegeRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        val system: System = systemRepository.save(mockSystem())

        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))
        privilegeRepository.save(mockPrivilege(name = "Signup", code = "SIGNUP", system = system))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        mockMvc.perform(
            get("/api/v1/privileges/page?systemId=${system.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `find all in list`() {
        val system: System = systemRepository.save(mockSystem())

        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))
        privilegeRepository.save(mockPrivilege(name = "Signup", code = "SIGNUP", system = system))

        val privileges: List<PrivilegeDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/privileges/list?systemId=${system.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(2, privileges.size)
    }

    @Test
    fun `get by id`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        val privilegeDto: PrivilegeDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/privileges/${privilege.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(privilege.id, privilegeDto.id)
    }

    @Test
    fun `create privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val request: CreatePrivilegeRequestDto = mockCreatePrivilegeRequestDto(systemId = system.id)

        val privilegeDto: PrivilegeDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/privileges")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isCreated).andReturn().response.contentAsString
        )

        assertEquals(1, privilegeRepository.count())
        assertNotNull(privilegeDto)
    }

    @Test
    fun `update privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))
        val request: UpdatePrivilegeRequestDto = mockUpdatePrivilegeRequestDto()

        val privilegeDto: PrivilegeDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/privileges/${privilege.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(privilege.id, privilegeDto.id)
    }

    @Test
    fun `delete privilege`() {
        val system: System = systemRepository.save(mockSystem())
        val privilege: Privilege = privilegeRepository.save(mockPrivilege(system = system))

        assertEquals(1, privilegeRepository.count())

        mockMvc.perform(
            delete("/api/v1/privileges/${privilege.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, privilegeRepository.count())
    }
}
