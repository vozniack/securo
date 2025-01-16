package dev.vozniack.securo.core.service

import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.UpdateTeamRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toTeam
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
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
class TeamService(private val teamRepository: TeamRepository) {

    fun findAll(query: Specificable<Team>, pageable: Pageable): Page<Team> =
        teamRepository.findAll(query.toSpecification(), pageable)

    fun findAll(query: Specificable<Team>): List<Team> =
        teamRepository.findAll(query.toSpecification())

    fun getById(id: UUID): Team = teamRepository.findById(id)
        .orElseThrow { NotFoundException("Not found team with id $id") }

    fun findById(id: UUID?): Team? = id?.let { teamRepository.findById(it).orElse(null) }

    fun create(request: CreateTeamRequestDto): Team {
        teamRepository.findByCode(request.code)?.let {
            throw ConflictException("Team with code ${request.code} already exists")
        }

        return teamRepository.save(request.toTeam())
    }

    fun update(id: UUID, request: UpdateTeamRequestDto): Team {
        val team: Team = getById(id)

        teamRepository.findByCode(request.code)
            ?.takeIf { it.id != id }
            ?.let { throw ConflictException("team with code ${request.code} already exists") }

        return teamRepository.save(
            team.apply {
                name = request.name
                code = request.code
                description = request.description
                updatedAt = LocalDateTime.now()
            }
        )
    }

    fun delete(id: UUID) = teamRepository.delete(
        getById(id).takeIf { it.scope == ScopeType.EXTERNAL }
            ?: throw ForbiddenException("Can't delete internal team")
    )
}
