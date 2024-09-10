package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.entity.User
import java.util.UUID
import org.springframework.data.jpa.domain.Specification

class UserQuery(
    private val scope: ScopeType? = null,
    private val firstName: String? = null,
    private val lastName: String? = null,
    private val email: String? = null,
    private val systemId: String? = null,
) : Specificable<User> {

    private fun scopeEquals(scope: ScopeType?): Specification<User> =
        Specification<User> { root, _, criteriaBuilder ->
            scope?.let { criteriaBuilder.equal(criteriaBuilder.upper(root.get("scope")), it.name) }
        }

    private fun firstNameLike(firstName: String?): Specification<User> =
        Specification<User> { root, _, criteriaBuilder ->
            firstName?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%${it.lowercase()}%") }
        }

    private fun lastNameLike(lastName: String?): Specification<User> =
        Specification<User> { root, _, criteriaBuilder ->
            lastName?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%${it.lowercase()}%") }
        }

    private fun emailLike(email: String?): Specification<User> =
        Specification<User> { root, _, criteriaBuilder ->
            email?.let { criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%${it.lowercase()}%") }
        }

    private fun belongsToSystemById(systemId: String?): Specification<User> =
        Specification<User> { root, _, criteriaBuilder ->
            systemId?.let {
                criteriaBuilder.equal(
                    root.join<System, User>("systems").get<UUID?>("id"), UUID.fromString(it)
                )
            }
        }

    override fun toSpecification(): Specification<User> =
        Specification<User> { _, _, _ -> null }
            .and(scopeEquals(scope))
            .and(belongsToSystemById(systemId))
            .and(
                firstNameLike(firstName)
                    .or(lastNameLike(lastName))
                    .or(emailLike(email))
            )
}
