package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserLanguageRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toUser
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.domain.repository.specification.Specificable
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import dev.vozniack.securo.core.utils.takeIfNotEmpty
import dev.vozniack.securo.core.utils.toLocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun findAll(query: Specificable<User>, pageable: Pageable): Page<User> =
        userRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<User>): List<User> =
        userRepository.findAll(query.toSpecification())

    fun getById(id: UUID): User = userRepository.findById(id)
        .orElseThrow { NotFoundException("Not found user with id $id") }

    fun getByEmail(email: String): User = userRepository.findByEmail(email)
        ?: throw NotFoundException("Not found user with email $email")

    fun create(request: CreateUserRequestDto): User {
        userRepository.findByEmail(request.email)?.let {
            throw ConflictException("User with email ${request.email} already exists")
        }

        return userRepository.save(request.toUser().apply { password = passwordEncoder.encode(request.password) })
    }

    fun update(id: UUID, request: UpdateUserRequestDto): User = userRepository.save(
        getById(id).apply {
            phonePrefix = request.phonePrefix.takeIfNotEmpty()
            phoneNumber = request.phoneNumber.takeIfNotEmpty()

            firstName = request.firstName
            lastName = request.lastName
            dateOfBirth = request.dateOfBirth.toLocalDate()

            country = request.country.takeIfNotEmpty()
            city = request.city.takeIfNotEmpty()
            zip = request.zip.takeIfNotEmpty()
            street = request.street.takeIfNotEmpty()
            house = request.house.takeIfNotEmpty()

            updatedAt = LocalDateTime.now()
        }
    )

    fun updatePassword(id: UUID, request: UpdateUserPasswordRequestDto): User = userRepository.save(
        getById(id).apply {
            password = passwordEncoder.encode(request.password)
            updatedAt = LocalDateTime.now()

        }
    )

    fun updateLanguage(id: UUID, request: UpdateUserLanguageRequestDto): User = userRepository.save(
        getById(id).apply {
            language = request.language
            updatedAt = LocalDateTime.now()
        }
    )

    fun delete(id: UUID) = userRepository.delete(getById(id))
}
