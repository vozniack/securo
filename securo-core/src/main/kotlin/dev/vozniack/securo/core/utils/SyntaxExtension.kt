package dev.vozniack.securo.core.utils

inline fun throwIfTrue(exception: Exception, block: () -> Boolean): Unit? = if (block()) throw exception else null
