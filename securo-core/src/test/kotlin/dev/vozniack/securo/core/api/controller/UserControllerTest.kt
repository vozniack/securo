package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.api.dto.entity.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserPasswordRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
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

class UserControllerTest @Autowired constructor(
    private val userRepository: UserRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun cleanUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        userRepository.save(mockUser(email = "john.doe@securo.com"))
        userRepository.save(mockUser(email = "jan.nowak@securo.pl"))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        mockMvc.perform(
            get("/api/v1/users/page")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `find all in list`() {
        userRepository.save(mockUser(email = "john.doe@securo.com"))
        userRepository.save(mockUser(email = "jan.nowak@securo.pl"))

        val users: List<UserDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/users/list")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(2, users.size)
    }

    @Test
    fun `get by id`() {
        val user: User = userRepository.save(mockUser())

        val userDto: UserDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/users/${user.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(user.id, userDto.id)
    }

    @Test
    fun `create user`() {
        val request = mockCreateUserRequestDto()

        val user: UserDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(1, userRepository.count())
        assertNotNull(user)
    }

    @Test
    fun `update user`() {
        val user: User = userRepository.save(mockUser())
        val request: UpdateUserRequestDto = mockUpdateUserRequestDto()

        val userDto: UserDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/users/${user.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(user.id, userDto.id)
    }

    @Test
    fun `update user password`() {
        val user: User = userRepository.save(mockUser())
        val request: UpdateUserPasswordRequestDto = mockUpdateUserPasswordRequestDto()

        val userDto: UserDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/users/${user.id}/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(user.id, userDto.id)
    }

    @Test
    fun `delete user`() {
        val user: User = userRepository.save(mockUser())

        mockMvc.perform(
            delete("/api/v1/users/${user.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, userRepository.count())
    }
}
