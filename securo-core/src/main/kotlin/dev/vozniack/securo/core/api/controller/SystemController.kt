package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.CreateSystemRequestDto
import dev.vozniack.securo.core.api.dto.SystemDto
import dev.vozniack.securo.core.api.dto.UpdateSystemRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toDto
import dev.vozniack.securo.core.api.extension.validator.validate
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.repository.specification.SystemQuery
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.SystemService
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
@RequestMapping("/api/v1/systems")
class SystemController(private val systemService: SystemService) {

    @GetMapping("/page")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) parents: Boolean?,
        pageable: Pageable
    ): Page<SystemDto> = systemService.findAll(
        SystemQuery(ScopeType.EXTERNAL, search, search, parents), pageable
    ).map { it.toDto() }

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) parents: Boolean?
    ): List<SystemDto> = systemService.findAll(
        SystemQuery(ScopeType.EXTERNAL, search, search, parents)
    ).map { it.toDto() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): SystemDto = systemService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateSystemRequestDto): SystemDto {
        request.validate().also {
            logger.debug { "Creating system with request $request" }
        }

        return systemService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateSystemRequestDto): SystemDto {
        request.validate().also {
            logger.debug { "Updating system $id with request $request" }
        }

        return systemService.update(id, request).toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting system $id" }

        systemService.delete(id)
    }

    companion object : KLogging()
}
