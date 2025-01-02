package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserLanguageRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.domain.repository.specification.UserQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserLanguageRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserPasswordRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
import dev.vozniack.securo.core.utils.toLocalDate
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        userRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        userRepository.save(mockUser(email = "john.doe@securo.com"))
        userRepository.save(mockUser(email = "jan.nowak@securo.pl"))

        val users: Page<User> = userService.findAll(UserQuery(), PageRequest.of(0, 24))

        assertEquals(2, users.content.size)
    }

    @Test
    fun `find all in list`() {
        userRepository.save(mockUser(email = "john.doe@securo.com"))
        userRepository.save(mockUser(email = "jan.nowak@securo.pl"))

        val users: List<User> = userService.findAll(UserQuery())

        assertEquals(2, users.size)
    }

    @Test
    fun `get user by id`() {
        val user: User = userRepository.save(mockUser())
        val fetchedUser: User = userService.getById(user.id)

        assertEquals(user.id, fetchedUser.id)
    }

    @Test
    fun `get not existing user by id`() {
        assertThrows<NotFoundException> {
            userService.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `create user`() {
        val request: CreateUserRequestDto = mockCreateUserRequestDto()
        val user: User = userService.create(request)

        assertEquals(1, userRepository.count())

        assertEquals(request.email, user.email)
        assertTrue(passwordEncoder.matches(request.password, user.password))
        assertEquals(request.firstName, user.firstName)
        assertEquals(request.lastName, user.lastName)
        assertEquals(request.dateOfBirth.toLocalDate(), user.dateOfBirth)
        assertEquals(request.language, user.language)
        assertTrue(user.active)
    }

    @Test
    fun `create user with already existing email`() {
        userRepository.save(mockUser())

        assertThrows<ConflictException> {
            userService.create(mockCreateUserRequestDto())
        }
    }

    @Test
    fun `update user`() {
        val user: User = userRepository.save(mockUser())

        val request: UpdateUserRequestDto = mockUpdateUserRequestDto()
        val updatedUser: User = userService.update(user.id, request)

        assertEquals(user.id, updatedUser.id)

        assertEquals(request.phonePrefix, updatedUser.phonePrefix)
        assertEquals(request.phoneNumber, updatedUser.phoneNumber)

        assertEquals(request.firstName, updatedUser.firstName)
        assertEquals(request.lastName, updatedUser.lastName)
        assertEquals(request.dateOfBirth.toLocalDate(), updatedUser.dateOfBirth)

        assertEquals(request.country, updatedUser.country)
        assertEquals(request.city, updatedUser.city)
        assertEquals(request.zip, updatedUser.zip)
        assertEquals(request.street, updatedUser.street)
        assertEquals(request.house, updatedUser.house)
    }

    @Test
    fun `update password`() {
        val user: User = userRepository.save(mockUser())

        val request: UpdateUserPasswordRequestDto = mockUpdateUserPasswordRequestDto()
        val updatedUser: User = userService.updatePassword(user.id, request)

        assertNotEquals(user.password, updatedUser.password)
        assertTrue(passwordEncoder.matches(request.password, updatedUser.password))
    }

    @Test
    fun `update language`() {
        val user: User = userRepository.save(mockUser())

        val request: UpdateUserLanguageRequestDto = mockUpdateUserLanguageRequestDto()
        val updatedUser: User = userService.updateLanguage(user.id, request)

        assertNotEquals(user.language, updatedUser.language)
        assertEquals(request.language, updatedUser.language)
    }

    @Test
    fun `delete user`() {
        val user: User = userRepository.save(mockUser())

        assertEquals(1, userRepository.count())

        userService.delete(user.id)

        assertEquals(0, userRepository.count())
    }
}
