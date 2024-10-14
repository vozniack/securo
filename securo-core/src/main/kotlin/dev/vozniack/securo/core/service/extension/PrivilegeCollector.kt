package dev.vozniack.securo.core.service.extension

import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.RelatedPrivilege
import dev.vozniack.securo.core.domain.entity.User
import java.util.UUID

fun collectPrivileges(user: User): List<Privilege> {
    val privileges: MutableList<Privilege> = mutableListOf()

    user.roles.forEach { collectPrivileges(it.privileges, privileges) }
    collectPrivileges(user.privileges, privileges)

    return privileges
}

private fun collectPrivileges(relatedPrivileges: List<RelatedPrivilege>, privileges: MutableList<Privilege>) {
    relatedPrivileges.filter { !it.excluded }
        .forEach { privileges.addPrivilegeIfNotExist(it.privilege) }

    relatedPrivileges.filter { it.excluded }
        .map { it.privilege.id }
        .forEach { excludePrivilege(privileges, it) }
}

private fun excludePrivilege(privileges: MutableList<Privilege>, excludedPrivilegeId: UUID) {
    privileges.removeIf { it.id == excludedPrivilegeId }

    privileges.filter { it.hasChildren() }
        .forEach { excludePrivilege(it.privileges, excludedPrivilegeId) }
}

infix fun MutableList<Privilege>.addPrivilegeIfNotExist(privilege: Privilege) {
    if (!this.flattenList(mutableListOf()).contains(privilege)) {
        this.add(privilege)
    }
}

private infix fun MutableList<Privilege>.flattenList(flattenedPrivileges: MutableList<Privilege>): MutableList<Privilege> {
    flattenedPrivileges.addAll(this)

    this.filter { it.hasChildren() }.forEach { it.privileges.flattenList(flattenedPrivileges) }

    return flattenedPrivileges
}

fun List<Privilege>.collectCodes(): List<String> = toMutableList().collectCodes(mutableListOf()).distinct()

private infix fun MutableList<Privilege>.collectCodes(privilegeCodes: MutableList<String>): MutableList<String> {
    privilegeCodes.addAll(this.filter { !it.hasChildren() }.map { "${it.system.code}_${it.code}" })

    this.filter { it.hasChildren() }.forEach { it.privileges.collectCodes(privilegeCodes) }

    return privilegeCodes
}
