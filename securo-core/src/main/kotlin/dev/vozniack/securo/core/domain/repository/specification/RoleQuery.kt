package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Role
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID
import org.springframework.data.jpa.domain.Specification

class RoleQuery(
    private val scope: ScopeType? = null,
    private val name: String? = null,
    private val code: String? = null,
    private val systemId: String? = null
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

    private fun belongsToSystemById(systemId: String?): Specification<Role> =
        Specification<Role> { root, _, criteriaBuilder ->
            systemId?.let {
                criteriaBuilder.equal(root.get<System?>("system").get<UUID?>("id"), UUID.fromString(it))
            }
        }

    override fun toSpecification(): Specification<Role> =
        Specification<Role> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(belongsToSystemById(systemId))
            .and(
                nameLike(name)
                    .or(codeLike(code))
            )
}
