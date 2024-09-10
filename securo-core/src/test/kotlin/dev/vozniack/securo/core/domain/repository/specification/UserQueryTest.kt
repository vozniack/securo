package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUser
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserQueryTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        userRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `user specification builder`() {
        val system: System = systemRepository.save(mockSystem())

        userRepository.save(mockUser(firstName = "John", lastName = "Doe", email = "john.doe@securo.com"))
        userRepository.save(mockUser(firstName = "Johny", lastName = "Doe", email = "johny.doe@securo.com"))
        userRepository.save(mockUser(firstName = "Jan", lastName = "Nowak", email = "jan.nowak@securo.pl"))

        var users: List<User> = userRepository.findAll(UserQuery().toSpecification())
        assertEquals(3, users.size)

        users = userRepository.findAll(UserQuery(lastName = "Doe").toSpecification())
        assertEquals(2, users.size)

        users = userRepository.findAll(UserQuery(firstName = "Jan").toSpecification())
        assertEquals(1, users.size)

        users = userRepository.findAll(UserQuery(systemId = system.id.toString()).toSpecification())
        assertEquals(0, users.size)
    }
}
