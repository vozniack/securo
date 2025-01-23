package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toEnvironment
import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.domain.entity.EnvironmentType
import dev.vozniack.securo.core.domain.repository.EnvironmentRepository
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class EnvironmentService(private val environmentRepository: EnvironmentRepository) {

    fun findAll(): Map<EnvironmentType, List<Environment>> = environmentRepository.findAll()
        .groupBy { it.type }.mapValues { (_, environments) -> environments.sortedBy { it.index } }

    fun getById(id: UUID): Environment = environmentRepository.findById(id)
        .orElseThrow { NotFoundException("Not found environment with id $id") }

    fun create(request: CreateEnvironmentRequestDto): Environment {
        environmentRepository.findByCodeAndType(request.code, request.type)?.let {
            throw ConflictException("Environment with code ${request.code} and type ${request.type} already exists")
        }

        val lastIndex = findAll().entries.filter { it.key == request.type }.lastIndex
        return environmentRepository.save(request.toEnvironment(lastIndex + 1))
    }

    fun update(id: UUID, request: UpdateEnvironmentRequestDto): Environment {
        environmentRepository.findByCodeAndType(request.code, request.type)?.takeIf { it.id != id }?.let {
            throw ConflictException("Environment with code ${request.code} and type ${request.type} already exists")
        }

        return environmentRepository.save(
            getById(id).apply {
                name = request.name
                code = request.code
                type = request.type
                description = request.description
                index = index
                updatedAt = LocalDateTime.now()
            }
        )
    }

    fun delete(id: UUID) = environmentRepository.delete(getById(id))
}
