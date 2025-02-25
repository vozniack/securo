package dev.vozniack.securo.core.domain.repository

import dev.vozniack.securo.core.domain.entity.Environment
import dev.vozniack.securo.core.domain.entity.EnvironmentType
import java.util.UUID
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface EnvironmentRepository : CrudRepository<Environment, UUID>,
    PagingAndSortingRepository<Environment, UUID>,
    JpaSpecificationExecutor<Environment> {

    fun findByType(type: EnvironmentType): List<Environment>

    fun findByCodeAndType(code: String, type: EnvironmentType): Environment?
}
