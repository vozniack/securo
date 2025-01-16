package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
import dev.vozniack.securo.core.utils.toStringLocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class UserMapperTest {

    @Test
    fun `map create user request to user`() {
        val request: CreateUserRequestDto = mockCreateUserRequestDto()
        val user: User = request.toUser()

        assertEquals(request.email, user.email)
        assertEquals(request.password, user.password)
        assertEquals(request.firstName, user.firstName)
        assertEquals(request.lastName, user.lastName)
        assertEquals(request.language, user.language)
        assertNotNull(user.createdAt)
        assertNull(user.updatedAt)
    }

    @Test
    fun `map user to dto`() {
        val user: User = mockUser()
        val userDto: UserDto = user.toDto()

        assertEquals(user.id, userDto.id)
        assertEquals(user.scope, userDto.scope)
        assertEquals(user.email, userDto.email)
        assertEquals(user.firstName, userDto.firstName)
        assertEquals(user.lastName, userDto.lastName)
        assertEquals(user.language, userDto.language)
        assertEquals(user.active, userDto.active)
        assertEquals(user.createdAt.toStringLocalDateTime(), userDto.createdAt)
        assertEquals(user.updatedAt?.toStringLocalDateTime(), userDto.updatedAt)
    }
}
