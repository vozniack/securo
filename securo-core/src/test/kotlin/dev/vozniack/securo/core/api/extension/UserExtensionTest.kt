package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserPasswordRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserExtensionTest {

    @Test
    fun `validate create user request`() {
        mockCreateUserRequestDto().validate()

        // missing first name
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(firstName = "").validate()
        }

        // missing last name
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(lastName = "").validate()
        }

        // missing language
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(language = "").validate()
        }

        // missing email
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(email = "").validate()
        }

        // missing password
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(password = "").validate()
        }

        // email without @ character
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(email = "john.doesecuro.com").validate()
        }

        // email without domain
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(email = "john.doe@securo").validate()
        }

        // password without capital letter
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(password = "password1!").validate()
        }

        // password without number
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(password = "pAssword!").validate()
        }

        // password without special character
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(password = "pAssword!").validate()
        }

        // language in not correct ISO form
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(language = "EN_en").validate()
        }
    }

    @Test
    fun `validate update user request`() {
        mockUpdateUserRequestDto().validate()

        // missing first name
        assertThrows<BadRequestException> {
            mockUpdateUserRequestDto(firstName = "").validate()
        }

        // missing last name
        assertThrows<BadRequestException> {
            mockUpdateUserRequestDto(lastName = "").validate()
        }

        // missing language
        assertThrows<BadRequestException> {
            mockUpdateUserRequestDto(language = "").validate()
        }


        // language in not correct ISO form
        assertThrows<BadRequestException> {
            mockUpdateUserRequestDto(language = "EN_en").validate()
        }
    }

    @Test
    fun `validate update user password request`() {
        mockUpdateUserPasswordRequestDto().validate()

        // password without capital letter
        assertThrows<BadRequestException> {
            mockUpdateUserPasswordRequestDto(password = "password1!").validate()
        }

        // password without number
        assertThrows<BadRequestException> {
            mockUpdateUserPasswordRequestDto(password = "pAssword!").validate()
        }

        // password without special character
        assertThrows<BadRequestException> {
            mockUpdateUserPasswordRequestDto(password = "pAssword!").validate()
        }
    }

    @Test
    fun `map create user request to user`() {
        val request: CreateUserRequestDto = mockCreateUserRequestDto()
        val user: User = request.toUser()

        assertEquals(request.email, user.email)
        assertEquals(request.password, user.password)
        assertEquals(request.firstName, user.firstName)
        assertEquals(request.lastName, user.lastName)
        assertEquals(request.language, user.language)
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
    }
}
