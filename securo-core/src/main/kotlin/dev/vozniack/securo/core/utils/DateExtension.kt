package dev.vozniack.securo.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.isCorrectDate(): Boolean = try {
    this.toLocalDate().let { true }
} catch (exception: Exception) {
    false
}

fun String.toLocalDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)

fun LocalDate.toStringLocalDate(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)

fun LocalDateTime.toStringLocalDateTime(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
