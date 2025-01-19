package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toRole
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.repository.RoleRepository
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
class RoleService(
    private val roleRepository: RoleRepository,
    private val teamService: TeamService
) {

    fun findAll(query: Specificable<Role>, pageable: Pageable): Page<Role> =
        roleRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<Role>): List<Role> =
        roleRepository.findAll(query.toSpecification())

    fun getById(id: UUID): Role = roleRepository.findById(id)
        .orElseThrow { NotFoundException("Not found role with id $id") }

    fun create(request: CreateRoleRequestDto): Role {
        roleRepository.findByCodeAndTeam(request.code, teamService.findById(request.teamId))?.let {
            throw ConflictException("Role with code ${request.code} within team ${request.teamId} already exists")
        }

        return roleRepository.save(request.toRole(teamService.getById(request.teamId)))
    }

    fun update(id: UUID, request: UpdateRoleRequestDto): Role {
        val role: Role = getById(id)

        roleRepository.findByCodeAndTeam(request.code, teamService.findById(role.team.id))
            ?.takeIf { it.id != id }
            ?.let { throw ConflictException("Role with code ${request.code} within team ${role.team.id} already exists") }

        return roleRepository.save(
            role.apply {
                name = request.name
                code = request.code
                description = request.description
                updatedAt = LocalDateTime.now()
            }
        )
    }

    fun delete(id: UUID) = roleRepository.delete(
        getById(id).takeIf { it.scope == ScopeType.EXTERNAL }
            ?: throw ForbiddenException("Can't delete internal role")
    )
}
