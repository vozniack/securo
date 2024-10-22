package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.CreateRoleRequestDto
import dev.vozniack.securo.core.api.dto.RoleDto
import dev.vozniack.securo.core.api.dto.UpdateRoleRequestDto
import dev.vozniack.securo.core.api.extension.toDto
import dev.vozniack.securo.core.api.extension.validate
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.repository.specification.RoleQuery
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.RoleService
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
@RequestMapping("/api/v1/roles")
class RoleController(private val roleService: RoleService) {

    @GetMapping("/page")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) systemId: String?,
        pageable: Pageable
    ): Page<RoleDto> = roleService.findAll(
        RoleQuery(ScopeType.EXTERNAL, search, search, systemId), pageable
    ).map { it.toDto() }

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) systemId: String?,
    ): List<RoleDto> = roleService.findAll(
        RoleQuery(ScopeType.EXTERNAL, search, search, systemId)
    ).map { it.toDto() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): RoleDto = roleService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateRoleRequestDto): RoleDto {
        request.validate().also {
            logger.debug { "Creating role with request $request" }
        }

        return roleService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateRoleRequestDto): RoleDto {
        request.validate().also {
            logger.debug { "Updating role $id with request $request" }
        }

        return roleService.update(id, request).toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting role $id" }

        roleService.delete(id)
    }

    companion object : KLogging()
}
