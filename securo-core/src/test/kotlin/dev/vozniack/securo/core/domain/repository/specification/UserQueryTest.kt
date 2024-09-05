package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.mock.mockUser
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserQueryTest @Autowired constructor(
    private val userRepository: UserRepository
) : AbstractUnitTest() {

    @AfterEach
    fun cleanUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `user specification builder`() {
        userRepository.save(mockUser(firstName = "John", lastName = "Doe", email = "john.doe@securo.com"))
        userRepository.save(mockUser(firstName = "Johny", lastName = "Doe", email = "johny.doe@securo.com"))
        userRepository.save(mockUser(firstName = "Jan", lastName = "Nowak", email = "jan.nowak@securo.pl"))

        var users = userRepository.findAll(UserQuery().toSpecification())
        Assertions.assertThat(users.size).isEqualTo(3)

        users = userRepository.findAll(UserQuery(lastName = "Doe").toSpecification())
        Assertions.assertThat(users.size).isEqualTo(2)

        users = userRepository.findAll(UserQuery(firstName = "Jan").toSpecification())
        Assertions.assertThat(users.size).isEqualTo(1)
    }
}
