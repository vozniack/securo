package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Team
import org.springframework.data.jpa.domain.Specification

class TeamQuery(
    private val scope: ScopeType? = null,
    private val name: String? = null,
    private val code: String? = null
) : Specificable<Team> {

    private fun scopeEquals(scope: ScopeType?): Specification<Team> =
        Specification<Team> { root, _, criteriaBuilder ->
            scope?.let { criteriaBuilder.equal(criteriaBuilder.upper(root.get("scope")), it.name) }
        }

    private fun nameLike(name: String?): Specification<Team> =
        Specification<Team> { root, _, criteriaBuilder ->
            name?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${it.lowercase()}%") }
        }

    private fun codeLike(code: String?): Specification<Team> =
        Specification<Team> { root, _, criteriaBuilder ->
            code?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%${it.lowercase()}%") }
        }

    override fun toSpecification(): Specification<Team> =
        Specification<Team> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(
                nameLike(name)
                    .or(codeLike(code))
            )
}
