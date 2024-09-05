package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.entity.extension.toUser
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.domain.repository.specification.Specificable
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.internal.logging.KLogging
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findAll(query: Specificable<User>, pageable: Pageable): Page<User> =
        userRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<User>): List<User> =
        userRepository.findAll(query.toSpecification())

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    fun getById(id: UUID): User = userRepository.findById(id)
        .orElseThrow { NotFoundException("Not found user with id $id") }

    fun create(request: CreateUserRequestDto): User {
        findByEmail(request.email)?.let { throw ConflictException("User with email ${request.email} already exists") }

        return userRepository.save(request.toUser()) // #todo encode
    }

    fun update(id: UUID, request: UpdateUserRequestDto): User = userRepository.save(
        getById(id).apply {
            firstName = request.firstName
            lastName = request.lastName
            language = request.language
        }
    )

    fun updatePassword(id: UUID, request: UpdateUserPasswordRequestDto): User = userRepository.save(
        getById(id).apply { password = request.password } // #todo encode
    )

    fun delete(id: UUID) = userRepository.delete(getById(id))

    companion object : KLogging()
}
