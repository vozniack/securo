package dev.vozniack.securo.core.domain.repository.specification

import org.springframework.data.jpa.domain.Specification

interface Specificable<T> {

    fun toSpecification(): Specification<T>
}
