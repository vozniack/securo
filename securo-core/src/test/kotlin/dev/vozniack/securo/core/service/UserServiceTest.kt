package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.domain.repository.specification.UserQuery
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.mock.mockCreateUserRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserPasswordRequestDto
import dev.vozniack.securo.core.mock.mockUpdateUserRequestDto
import dev.vozniack.securo.core.mock.mockUser
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

class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository
) : AbstractUnitTest() {

    @AfterEach
    fun cleanUp() {
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
    fun `find user by email`() {
        val user: User = userRepository.save(mockUser())
        val fetchedUSer: User? = userService.findByEmail(user.email)

        assertEquals(user, fetchedUSer)
    }

    @Test
    fun `find not existing user by email`() {
        val fetchedUser: User? = userService.findByEmail("someemail@somedomain.com")

        assertTrue(fetchedUser == null)
    }

    @Test
    fun `get user by id`() {
        val user: User = userRepository.save(mockUser())
        val fetchedUSer: User = userService.getById(user.id)

        assertEquals(user, fetchedUSer)
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

        val fetchedUser: User = userRepository.findById(user.id).get()

        assertEquals(request.email, fetchedUser.email)
        assertEquals(request.password, fetchedUser.password) // #todo encode
        assertEquals(request.firstName, fetchedUser.firstName)
        assertEquals(request.lastName, fetchedUser.lastName)
        assertEquals(request.language, fetchedUser.language)
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

        assertEquals(updatedUser.firstName, request.firstName)
        assertEquals(updatedUser.lastName, request.lastName)
        assertEquals(updatedUser.language, request.language)
    }

    @Test
    fun `update password`() {
        val user: User = userRepository.save(mockUser())

        val request: UpdateUserPasswordRequestDto = mockUpdateUserPasswordRequestDto()
        val updatedUser: User = userService.updatePassword(user.id, request)

        assertNotEquals(user.password, updatedUser.password)
        assertEquals(request.password, updatedUser.password) // #todo encode
    }

    @Test
    fun `delete user`() {
        val user: User = userRepository.save(mockUser())

        assertEquals(1, userRepository.count())

        userService.delete(user.id)

        assertEquals(0, userRepository.count())
    }
}