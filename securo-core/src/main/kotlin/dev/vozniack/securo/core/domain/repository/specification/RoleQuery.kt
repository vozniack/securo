package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.Team
import java.util.UUID
import org.springframework.data.jpa.domain.Specification

class RoleQuery(
    private val scope: ScopeType? = null,
    private val name: String? = null,
    private val code: String? = null,
    private val teamId: String? = null
) : Specificable<Role> {

    private fun scopeEquals(scope: ScopeType?): Specification<Role> =
        Specification<Role> { root, _, criteriaBuilder ->
            scope?.let { criteriaBuilder.equal(criteriaBuilder.upper(root.get("scope")), it.name) }
        }

    private fun nameLike(name: String?): Specification<Role> =
        Specification<Role> { root, _, criteriaBuilder ->
            name?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${it.lowercase()}%") }
        }

    private fun codeLike(code: String?): Specification<Role> =
        Specification<Role> { root, _, criteriaBuilder ->
            code?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%${it.lowercase()}%") }
        }

    private fun belongsToTeamById(teamId: String?): Specification<Role> =
        Specification<Role> { root, _, criteriaBuilder ->
            teamId?.let {
                criteriaBuilder.equal(root.get<Team?>("team").get<UUID?>("id"), UUID.fromString(it))
            }
        }

    override fun toSpecification(): Specification<Role> =
        Specification<Role> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(belongsToTeamById(teamId))
            .and(
                nameLike(name)
                    .or(codeLike(code))
            )
}
