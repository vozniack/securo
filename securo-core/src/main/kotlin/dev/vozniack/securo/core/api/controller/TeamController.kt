package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.TeamDto
import dev.vozniack.securo.core.api.dto.UpdateTeamRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toDto
import dev.vozniack.securo.core.api.extension.validator.validate
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.repository.specification.TeamQuery
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.TeamService
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/teams")
class TeamController(private val teamService: TeamService) {

    @GetMapping("/page")
    fun findAll(
        @RequestParam(required = false) search: String?,
        pageable: Pageable
    ): Page<TeamDto> = teamService.findAll(
        TeamQuery(ScopeType.EXTERNAL, search, search), pageable
    ).map { it.toDto() }

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?,
    ): List<TeamDto> = teamService.findAll(
        TeamQuery(ScopeType.EXTERNAL, search, search)
    ).map { it.toDto() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): TeamDto = teamService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTeamRequestDto): TeamDto {
        request.validate().also {
            logger.debug { "Creating team with request $request" }
        }

        return teamService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateTeamRequestDto): TeamDto {
        request.validate().also {
            logger.debug { "Updating team $id with request $request" }
        }

        return teamService.update(id, request).toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting team $id" }

        teamService.delete(id)
    }

    companion object : KLogging()
}
