package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.CreatePrivilegeRequestDto
import dev.vozniack.securo.core.api.dto.PrivilegeDto
import dev.vozniack.securo.core.api.dto.UpdatePrivilegeRequestDto
import dev.vozniack.securo.core.api.extension.toDto
import dev.vozniack.securo.core.api.extension.validate
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.repository.specification.PrivilegeQuery
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.PrivilegeService
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
@RequestMapping("/api/v1/privileges")
class PrivilegeController(private val privilegeService: PrivilegeService) {

    @GetMapping("/page")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) parents: Boolean?,
        @RequestParam(required = false) systemId: String?,
        pageable: Pageable
    ): Page<PrivilegeDto> = privilegeService.findAll(
        PrivilegeQuery(ScopeType.EXTERNAL, search, search, parents, systemId), pageable
    ).map { it.toDto() }

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) parents: Boolean?,
        @RequestParam(required = false) systemId: String?,
    ): List<PrivilegeDto> = privilegeService.findAll(
        PrivilegeQuery(ScopeType.EXTERNAL, search, search, parents, systemId)
    ).map { it.toDto() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): PrivilegeDto = privilegeService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreatePrivilegeRequestDto): PrivilegeDto {
        request.validate().also {
            logger.debug { "Creating privilege with request $request" }
        }

        return privilegeService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdatePrivilegeRequestDto): PrivilegeDto {
        request.validate().also {
            logger.debug { "Updating privilege $id with request $request" }
        }

        return privilegeService.update(id, request).toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting privilege $id" }

        privilegeService.delete(id)
    }

    companion object : KLogging()
}
