package dev.vozniack.securo.core.domain.entity.extension

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.entity.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserExtensionTest {

    @Test
    fun `map user to dto`() {
        val user: User = mockUser()
        val userDto: UserDto = user.toDto()

        assertEquals(user.id, userDto.id)
        assertEquals(user.email, userDto.email)
        assertEquals(user.firstName, userDto.firstName)
        assertEquals(user.lastName, userDto.lastName)
        assertEquals(user.language, userDto.language)
        assertEquals(user.active, userDto.active)
    }

    @Test
    fun `create user request to user`() {
        val request: CreateUserRequestDto = mockCreateUserRequestDto()
        val user: User = request.toUser()

        assertEquals(request.email, user.email)
        assertEquals(request.password, user.password)
        assertEquals(request.firstName, user.firstName)
        assertEquals(request.lastName, user.lastName)
        assertEquals(request.language, user.language)
    }
}
