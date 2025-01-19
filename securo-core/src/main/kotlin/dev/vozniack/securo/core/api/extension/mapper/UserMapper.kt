package dev.vozniack.securo.core.api.extension.mapper

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.utils.toLocalDate
import dev.vozniack.securo.core.utils.toStringLocalDate
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateUserRequestDto.toUser(): User = User(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth.toLocalDate(),
    language = language
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    scope = scope,
    email = email,
    phonePrefix = phonePrefix,
    phoneNumber = phoneNumber,
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth.toStringLocalDate(),
    language = language,
    country = country,
    city = city,
    zip = zip,
    street = street,
    house = house,
    active = active,
    createdAt = createdAt.toStringLocalDateTime(),
    updatedAt = updatedAt?.toStringLocalDateTime()
)
