package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.domain.entity.User
import org.springframework.data.jpa.domain.Specification

class UserQuery(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
) : Specificable<User> {

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

    override fun toSpecification(): Specification<User> =
        Specification<User> { _, _, _ -> null }
            .and(
                firstNameLike(firstName)
                    .or(lastNameLike(lastName))
                    .or(emailLike(email))
            )
}
