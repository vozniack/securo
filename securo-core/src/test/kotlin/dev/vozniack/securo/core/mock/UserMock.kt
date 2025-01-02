package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserLanguageRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.utils.toStringLocalDate
import java.time.LocalDate
import java.util.UUID

fun mockUser(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
    firstName: String = "John",
    lastName: String = "Doe",
    dateOfBirth: LocalDate = LocalDate.now().minusYears(24),
    language: String = "en_EN"
): User = User(
    id = UUID.randomUUID(),
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    language = language
)

fun mockCreateUserRequestDto(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
    firstName: String = "John",
    lastName: String = "Doe",
    dateOfBirth: String = LocalDate.now().minusYears(24).toStringLocalDate(),
    language: String = "en_EN"
): CreateUserRequestDto = CreateUserRequestDto(
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    language = language
)

fun mockUpdateUserRequestDto(
    firstName: String = "Jan",
    lastName: String = "Nowak",
    dateOfBirth: String = LocalDate.now().minusYears(25).toStringLocalDate(),
): UpdateUserRequestDto = UpdateUserRequestDto(
    phonePrefix = "+48",
    phoneNumber = "226567600",
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    country = "PL",
    city = "Warsaw",
    zip = "00-901",
    street = "Plac defilad",
    house = "1"
)

fun mockUpdateUserPasswordRequestDto(
    password: String = "Password123!"
): UpdateUserPasswordRequestDto = UpdateUserPasswordRequestDto(password = password)

fun mockUpdateUserLanguageRequestDto(
    language: String = "pl_PL"
): UpdateUserLanguageRequestDto = UpdateUserLanguageRequestDto(language = language)
