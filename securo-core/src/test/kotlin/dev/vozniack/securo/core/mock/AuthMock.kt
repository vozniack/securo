package dev.vozniack.securo.core.mock

import dev.vozniack.securo.core.api.dto.LoginRequest

fun mockLoginRequest(
    email: String = "john.doe@securo.com",
    password: String = "Admin123!",
): LoginRequest = LoginRequest(email = email, password = password)
