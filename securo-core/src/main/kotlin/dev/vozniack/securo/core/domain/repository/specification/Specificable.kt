package dev.vozniack.securo.core.domain.repository.specification

import org.springframework.data.jpa.domain.Specification

// #todo improve specifications

interface Specificable<T> {

    fun toSpecification(): Specification<T>
}
