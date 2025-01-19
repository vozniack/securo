package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserLanguageRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserPasswordRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserRequestDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserValidatorTest {

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

        // missing date of birth
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(dateOfBirth = "").validate()
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

        // date of birth in not correct ISO form
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(dateOfBirth = "01-01-1990").validate()
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

        // missing date of birth
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(dateOfBirth = "").validate()
        }

        // date of birth in not correct ISO form
        assertThrows<BadRequestException> {
            mockCreateUserRequestDto(dateOfBirth = "01-01-1990").validate()
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
    fun `validate update user language request`() {
        mockUpdateUserLanguageRequestDto().validate()

        // missing language
        assertThrows<BadRequestException> {
            mockUpdateUserLanguageRequestDto(language = "").validate()
        }

        // language in not correct ISO form
        assertThrows<BadRequestException> {
            mockUpdateUserLanguageRequestDto(language = "EN_en").validate()
        }
    }
}
