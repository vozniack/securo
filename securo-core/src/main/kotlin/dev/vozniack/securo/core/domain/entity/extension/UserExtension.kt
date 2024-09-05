package dev.vozniack.securo.core.domain.entity.extension

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.domain.entity.User

fun User.toDto(): UserDto = UserDto(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    language = language,
    active = active,
)

fun CreateUserRequestDto.toUser(): User = User(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    language = language
)
