package dev.vozniack.securo.core.utils

import dev.vozniack.securo.core.utils.RegexConstants.EMAIL_REGEX
import dev.vozniack.securo.core.utils.RegexConstants.LANGUAGE_REGEX
import dev.vozniack.securo.core.utils.RegexConstants.PASSWORD_REGEX

fun String.matchesEmailRegex() = matches(EMAIL_REGEX.toRegex())

fun String.matchesPasswordRegex() = matches(PASSWORD_REGEX.toRegex())

fun String.matchesLanguageRegex() = matches(LANGUAGE_REGEX.toRegex())

private object RegexConstants {
    const val EMAIL_REGEX: String = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    const val PASSWORD_REGEX: String = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[\$&+:;=?@#|'<>^*()%!-]).{6,}\$"
    const val LANGUAGE_REGEX: String = "^[a-z]{2}_[A-Z]{2}\$"
}
