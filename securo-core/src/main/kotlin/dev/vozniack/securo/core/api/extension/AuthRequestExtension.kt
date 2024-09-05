package dev.vozniack.securo.core.api.extension

import dev.vozniack.securo.core.api.dto.LoginRequest
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.utils.throwIfTrue

fun LoginRequest.validate() {
    throwIfTrue(BadRequestException("Email can't be empty")) { email.isEmpty() }
    throwIfTrue(BadRequestException("Password can't be empty")) { password.isEmpty() }
}
