package dev.vozniack.securo.core.domain.repository

import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemRepository : CrudRepository<System, UUID>,
    PagingAndSortingRepository<System, UUID>,
    JpaSpecificationExecutor<System> {

    fun findByCode(code: String): System?
}
