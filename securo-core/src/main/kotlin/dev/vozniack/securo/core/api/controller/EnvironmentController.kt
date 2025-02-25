package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.controller.SystemController.Companion.logger
import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.EnvironmentDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentsOrderRequestDto
import dev.vozniack.securo.core.api.extension.mapper.toDto
import dev.vozniack.securo.core.api.extension.validator.validate
import dev.vozniack.securo.core.domain.entity.EnvironmentType
import dev.vozniack.securo.core.service.EnvironmentService
import java.util.UUID
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
@RequestMapping("/api/v1/environments")
class EnvironmentController(private val environmentService: EnvironmentService) {

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?
    ): Map<EnvironmentType, List<EnvironmentDto>> = environmentService.findAll()
        .mapValues { (_, environments) -> environments.map { it.toDto() } }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): EnvironmentDto = environmentService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateEnvironmentRequestDto): EnvironmentDto {
        request.validate().also {
            logger.debug { "Creating environment with request $request" }
        }

        return environmentService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateEnvironmentRequestDto): EnvironmentDto {
        request.validate().also {
            logger.debug { "Updating environment $id with request $request" }
        }

        return environmentService.update(id, request).toDto()
    }

    @PutMapping("/order")
    fun updateEnvironmentsOrder(@RequestBody request: UpdateEnvironmentsOrderRequestDto): List<EnvironmentDto> {
        request.validate().also {
            logger.debug { "Updating ${request.type} environments order with request $request" }
        }

        return environmentService.updateOrder(request).map { it.toDto() }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting environment $id" }

        environmentService.delete(id)
    }
}
