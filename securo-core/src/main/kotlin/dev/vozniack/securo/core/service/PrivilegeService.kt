package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.UpdatePrivilegeRequestDto
import dev.vozniack.securo.core.api.extension.toPrivilege
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.repository.PrivilegeRepository
import dev.vozniack.securo.core.domain.repository.specification.Specificable
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PrivilegeService(
    private val privilegeRepository: PrivilegeRepository,
    private val systemService: SystemService
) {

    fun findAll(query: Specificable<Privilege>, pageable: Pageable): Page<Privilege> =
        privilegeRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<Privilege>): List<Privilege> =
        privilegeRepository.findAll(query.toSpecification())

    fun getById(id: UUID): Privilege = privilegeRepository.findById(id)
        .orElseThrow { NotFoundException("Not found privilege with id $id") }

    fun findById(id: UUID?): Privilege? = id?.let { privilegeRepository.findByIdOrNull(it) }

    fun create(request: CreatePrivilegeRequestDto): Privilege {
        privilegeRepository.findByCodeAndSystem(request.code, systemService.findById(request.systemId))?.let {
            throw ConflictException("Privilege with code ${request.code} within system ${request.systemId} already exists")
        }

        return privilegeRepository.save(
            request.toPrivilege(
                systemService.getById(request.systemId),
                findById(request.parentId)
            )
        )
    }

    fun update(id: UUID, request: UpdatePrivilegeRequestDto): Privilege {
        val privilege: Privilege = getById(id)

        privilegeRepository.findByCodeAndSystem(request.code, systemService.findById(privilege.system.id))
            ?.takeIf { it.id != id }
            ?.let { throw ConflictException("Privilege with code ${request.code} within system ${privilege.system.id} already exists") }

        return privilegeRepository.save(
            privilege.apply {
                name = request.name
                code = request.code
                description = request.description
            }
        )
    }

    fun delete(id: UUID) = privilegeRepository.delete(
        getById(id).takeIf { it.scope == ScopeType.EXTERNAL }
            ?: throw ForbiddenException("Can't delete internal privilege")
    )
}
