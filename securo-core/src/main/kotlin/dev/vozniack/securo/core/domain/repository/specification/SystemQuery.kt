package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.System
import org.springframework.data.jpa.domain.Specification

class SystemQuery(
    private val scope: ScopeType? = null,
    private val name: String? = null,
    private val code: String? = null,
    private val parents: Boolean? = null,
) : Specificable<System> {

    private fun scopeEquals(scope: ScopeType?): Specification<System> =
        Specification<System> { root, _, criteriaBuilder ->
            scope?.let { criteriaBuilder.equal(criteriaBuilder.upper(root.get("scope")), it.name) }
        }

    private fun nameLike(name: String?): Specification<System> =
        Specification<System> { root, _, criteriaBuilder ->
            name?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${it.lowercase()}%") }
        }

    private fun codeLike(code: String?): Specification<System> =
        Specification<System> { root, _, criteriaBuilder ->
            code?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%${it.lowercase()}%") }
        }


    private fun isParent(isParent: Boolean?): Specification<System> =
        Specification<System> { root, _, criteriaBuilder ->
            isParent?.let {
                criteriaBuilder.isNull(root.get<System?>("parent"))
            }
        }

    override fun toSpecification(): Specification<System> =
        Specification<System> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(isParent(parents))
            .and(
                nameLike(name)
                    .or(codeLike(code))
            )
}
