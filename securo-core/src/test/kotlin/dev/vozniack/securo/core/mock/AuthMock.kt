package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.LoginRequestDto

fun mockLoginRequest(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
): LoginRequestDto = LoginRequestDto(email = email, password = password)
