package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.api.dto.CreateUserRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserLanguageRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserPasswordRequestDto
import dev.vozniack.securo.core.api.dto.UpdateUserRequestDto
import dev.vozniack.securo.core.api.dto.UserDto
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.utils.isCorrectDate
import dev.vozniack.securo.core.utils.matchesEmailRegex
import dev.vozniack.securo.core.utils.matchesLanguageRegex
import dev.vozniack.securo.core.utils.matchesPasswordRegex
import dev.vozniack.securo.core.utils.throwIfTrue
import dev.vozniack.securo.core.utils.toStringLocalDate
import dev.vozniack.securo.core.utils.toLocalDate
import dev.vozniack.securo.core.utils.toStringLocalDateTime

fun CreateUserRequestDto.validate() {
    throwIfTrue(BadRequestException("Email can't be empty")) { email.isEmpty() }
    throwIfTrue(BadRequestException("Password can't be empty")) { password.isEmpty() }
    throwIfTrue(BadRequestException("First name can't be empty")) { firstName.isEmpty() }
    throwIfTrue(BadRequestException("Last name can't be empty")) { lastName.isEmpty() }
    throwIfTrue(BadRequestException("Date of birth can't be empty")) { dateOfBirth.isEmpty() }
    throwIfTrue(BadRequestException("Language can't be empty")) { language.isEmpty() }

    throwIfTrue(BadRequestException("Email must be a valid address")) {
        !email.matchesEmailRegex()
    }

    throwIfTrue(BadRequestException("Password must be 6 characters length and contain capital letter, number and special character")) {
        !password.matchesPasswordRegex()
    }

    throwIfTrue(BadRequestException("Date must be in ISO format (yyyy-MM-dd)")) {
        !dateOfBirth.isCorrectDate()
    }

    throwIfTrue(BadRequestException("Language must be in ISO form, e.g. en_US")) {
        !language.matchesLanguageRegex()
    }
}

fun UpdateUserRequestDto.validate() {
    throwIfTrue(BadRequestException("First name can't be empty")) { firstName.isEmpty() }
    throwIfTrue(BadRequestException("Last name can't be empty")) { lastName.isEmpty() }
    throwIfTrue(BadRequestException("Date of birth can't be empty")) { dateOfBirth.isEmpty() }

    throwIfTrue(BadRequestException("Date must be in ISO format (yyyy-MM-dd)")) {
        !dateOfBirth.isCorrectDate()
    }
}

fun UpdateUserPasswordRequestDto.validate() {
    throwIfTrue(BadRequestException("Password can't be empty")) { password.isEmpty() }

    throwIfTrue(BadRequestException("Password must be 6 characters length and contain capital letter, number and special character")) {
        !password.matchesPasswordRegex()
    }
}

fun UpdateUserLanguageRequestDto.validate() {
    throwIfTrue(BadRequestException("Language can't be empty")) { language.isEmpty() }

    throwIfTrue(BadRequestException("Language must be in ISO form, e.g. en_US")) {
        !language.matchesLanguageRegex()
    }
}

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
