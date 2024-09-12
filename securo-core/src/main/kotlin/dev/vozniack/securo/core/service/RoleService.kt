package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.api.extension.toRole
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.specification.Specificable
import dev.vozniack.securo.core.internal.exception.ConflictException
import dev.vozniack.securo.core.internal.exception.ForbiddenException
import dev.vozniack.securo.core.internal.exception.NotFoundException
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val systemService: SystemService
) {

    fun findAll(query: Specificable<Role>, pageable: Pageable): Page<Role> =
        roleRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<Role>): List<Role> =
        roleRepository.findAll(query.toSpecification())

    fun getById(id: UUID): Role = roleRepository.findById(id)
        .orElseThrow { NotFoundException("Not found role id $id") }

    fun create(request: CreateRoleRequestDto): Role {
        roleRepository.findByCodeAndSystem(request.code, systemService.findById(request.systemId))?.let {
            throw ConflictException("Role with code ${request.code} within system ${request.systemId} already exists")
        }

        return roleRepository.save(request.toRole(systemService.getById(request.systemId)))
    }

    fun update(id: UUID, request: UpdateRoleRequestDto): Role {
        roleRepository.findByCodeAndSystem(request.code, systemService.findById(request.systemId))
            ?.takeIf { it.id != id }
            ?.let { throw ConflictException("Role with code ${request.code} within system ${request.systemId} already exists") }

        return roleRepository.save(
            getById(id).takeIf { it.system.id == request.systemId }?.apply {
                name = request.name
                code = request.code
                description = request.description
            } ?: throw ConflictException("Role $id can't be moved to system ${request.systemId}")
        )
    }

    fun delete(id: UUID) = roleRepository.delete(
        getById(id).takeIf { it.scope == ScopeType.EXTERNAL }
            ?: throw ForbiddenException("Can't delete internal role")
    )
}
