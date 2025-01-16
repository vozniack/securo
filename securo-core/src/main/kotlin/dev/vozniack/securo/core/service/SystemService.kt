package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toSystem
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.specification.Specificable
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SystemService(private val systemRepository: SystemRepository) {

    fun findAll(query: Specificable<System>, pageable: Pageable): Page<System> =
        systemRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<System>): List<System> =
        systemRepository.findAll(query.toSpecification())

    fun getById(id: UUID): System = systemRepository.findById(id)
        .orElseThrow { NotFoundException("Not found system with id $id") }

    fun findById(id: UUID?): System? = id?.let { systemRepository.findById(it).orElse(null) }

    fun create(request: CreateSystemRequestDto): System {
        systemRepository.findByCode(request.code)?.let {
            throw ConflictException("System with code ${request.code} already exists")
        }

        return systemRepository.save(request.toSystem(findById(request.parentId)))
    }

    fun update(id: UUID, request: UpdateSystemRequestDto): System {
        systemRepository.findByCode(request.code)?.takeIf { it.id != id }?.let {
            throw ConflictException("System with code ${request.code} already exists")
        }

        return systemRepository.save(
            getById(id).apply {
                name = request.name
                code = request.code
                description = request.description
                icon = request.icon
                parent = findById(request.parentId)
                updatedAt = LocalDateTime.now()
            }
        )
    }

    fun delete(id: UUID) = systemRepository.delete(
        getById(id).takeIf { it.scope == ScopeType.EXTERNAL }
            ?: throw ForbiddenException("Can't delete internal system")
    )
}
