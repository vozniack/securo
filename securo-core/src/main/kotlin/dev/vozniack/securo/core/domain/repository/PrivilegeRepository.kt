package dev.vozniack.securo.core.domain.repository

import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PrivilegeRepository : CrudRepository<Privilege, UUID>,
    PagingAndSortingRepository<Privilege, UUID>,
    JpaSpecificationExecutor<Privilege> {

    fun findByCodeAndSystem(code: String, system: System?): Privilege?
}
