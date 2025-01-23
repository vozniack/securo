package dev.vozniack.securo.core.api.extension.validator

import dev.vozniack.securo.core.api.dto.CreateEnvironmentRequestDto
import dev.vozniack.securo.core.api.dto.UpdateEnvironmentRequestDto
import dev.vozniack.securo.core.internal.exception.BadRequestException
import dev.vozniack.securo.core.utils.throwIfTrue

fun CreateEnvironmentRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}

fun UpdateEnvironmentRequestDto.validate() {
    throwIfTrue(BadRequestException("Name can't be empty")) { name.isEmpty() }
    throwIfTrue(BadRequestException("Code can't be empty")) { code.isEmpty() }
}
