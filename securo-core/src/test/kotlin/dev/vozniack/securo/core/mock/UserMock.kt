package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.domain.entity.User
import java.util.UUID

fun mockUser(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
    firstName: String = "John",
    lastName: String = "Doe",
    language: String = "en_EN"
): User = User(
    id = UUID.randomUUID(),
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    language = language
)

fun mockCreateUserRequestDto(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
    firstName: String = "John",
    lastName: String = "Doe",
    language: String = "en_EN"
): CreateUserRequestDto = CreateUserRequestDto(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    language = language
)

fun mockUpdateUserRequestDto(
    firstName: String = "Jan",
    lastName: String = "Nowak",
    language: String = "pl_PL"
): UpdateUserRequestDto = UpdateUserRequestDto(
    firstName = firstName,
    lastName = lastName,
    language = language
)

fun mockUpdateUserPasswordRequestDto(
    password: String = "Password123!"
): UpdateUserPasswordRequestDto = UpdateUserPasswordRequestDto(password = password)
