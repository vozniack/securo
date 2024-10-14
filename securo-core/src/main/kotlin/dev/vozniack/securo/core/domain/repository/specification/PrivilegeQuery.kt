package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import java.util.UUID
import org.springframework.data.jpa.domain.Specification

class PrivilegeQuery(
    private val scope: ScopeType? = null,
    private val name: String? = null,
    private val code: String? = null,
    private val parent: Boolean? = null,
    private val systemId: String? = null
) : Specificable<Privilege> {

    private fun scopeEquals(scope: ScopeType?): Specification<Privilege> =
        Specification<Privilege> { root, _, criteriaBuilder ->
            scope?.let { criteriaBuilder.equal(criteriaBuilder.upper(root.get("scope")), it.name) }
        }

    private fun nameLike(name: String?): Specification<Privilege> =
        Specification<Privilege> { root, _, criteriaBuilder ->
            name?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${it.lowercase()}%") }
        }

    private fun codeLike(code: String?): Specification<Privilege> =
        Specification<Privilege> { root, _, criteriaBuilder ->
            code?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%${it.lowercase()}%") }
        }

    private fun isParent(isParent: Boolean?): Specification<Privilege> =
        Specification<Privilege> { root, _, criteriaBuilder ->
            isParent?.let {
                criteriaBuilder.isNull(root.get<Privilege?>("parent"))
            }
        }

    private fun belongsToSystemById(systemId: String?): Specification<Privilege> =
        Specification<Privilege> { root, _, criteriaBuilder ->
            systemId?.let {
                criteriaBuilder.equal(root.get<System?>("system").get<UUID?>("id"), UUID.fromString(it))
            }
        }

    override fun toSpecification(): Specification<Privilege> =
        Specification<Privilege> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(belongsToSystemById(systemId))
            .and(isParent(parent))
            .and(
                nameLike(name)
                    .or(codeLike(code))
            )
}
