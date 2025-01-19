package dev.vozniack.securo.core.domain.repository

import dev.vozniack.securo.core.domain.entity.Team
import java.util.UUID
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : CrudRepository<Team, UUID>,
    PagingAndSortingRepository<Team, UUID>,
    JpaSpecificationExecutor<Team> {

    fun findByCode(code: String): Team?
}
