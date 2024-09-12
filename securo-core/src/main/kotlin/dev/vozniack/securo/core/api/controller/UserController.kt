package dev.vozniack.securo.core.api.controller

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.api.extension.toDto
import dev.vozniack.securo.core.api.extension.validate
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.repository.specification.UserQuery
import dev.vozniack.securo.core.internal.logging.KLogging
import dev.vozniack.securo.core.service.UserService
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
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/page")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) systemId: String?,
        pageable: Pageable
    ): Page<UserDto> = userService.findAll(
        UserQuery(ScopeType.EXTERNAL, search, search, search, systemId), pageable
    ).map { it.toDto() }

    @GetMapping("/list")
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) systemId: String?,
    ): List<UserDto> = userService.findAll(
        UserQuery(ScopeType.EXTERNAL, search, search, search, systemId)
    ).map { it.toDto() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): UserDto = userService.getById(id).toDto()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateUserRequestDto): UserDto {
        request.validate().also {
            logger.debug { "Creating user with request $request" }
        }

        return userService.create(request).toDto()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateUserRequestDto): UserDto {
        request.validate().also {
            logger.debug { "Updating user $id with request $request" }
        }

        return userService.update(id, request).toDto()
    }

    @PutMapping("/{id}/password")
    fun updatePassword(@PathVariable id: UUID, @RequestBody request: UpdateUserPasswordRequestDto): UserDto {
        request.validate().also {
            logger.debug { "Updating user $id password" }
        }

        return userService.updatePassword(id, request).toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        logger.debug { "Deleting user $id" }

        userService.delete(id)
    }

    companion object : KLogging()
}
