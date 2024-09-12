package dev.vozniack.securo.core.domain.repository

import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : CrudRepository<Role, UUID>,
    PagingAndSortingRepository<Role, UUID>,
    JpaSpecificationExecutor<Role> {

    fun findByCodeAndSystem(code: String, system: System?): Role?
}
