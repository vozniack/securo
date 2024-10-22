package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.PrivilegeRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockSystem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

class PrivilegeQueryTest @Autowired constructor(
    private val privilegeRepository: PrivilegeRepository,
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        privilegeRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    @Transactional
    fun `privilege specification builder`() {
        val system: System = systemRepository.save(mockSystem())
        val externalSystem: System = systemRepository.save(mockSystem(name = "External system", code = "EXT"))

        privilegeRepository.save(mockPrivilege(name = "Signup", code = "SIGNUP", system = system))
        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))
        privilegeRepository.save(mockPrivilege(name = "Action 1", code = "ACT1", system = system))
        privilegeRepository.save(mockPrivilege(name = "Action 2", code = "ACT2", system = system))
        privilegeRepository.save(mockPrivilege(name = "External action", code = "EXACT", system = externalSystem))

        val parent: Privilege = privilegeRepository.save(mockPrivilege(name = "Parent", code = "PAR", system = system))
        privilegeRepository.save(mockPrivilege(name = "Child", code = "CHD", system = system, parent = parent))

        var privileges: List<Privilege> = privilegeRepository.findAll(PrivilegeQuery().toSpecification())
        assertEquals(7, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(name = "Signup").toSpecification())
        assertEquals(1, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(code = "ACT").toSpecification())
        assertEquals(3, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(code = "ACT", systemId = system.id.toString()).toSpecification())
        assertEquals(2, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(systemId = externalSystem.id.toString()).toSpecification())
        assertEquals(1, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(parent = true).toSpecification())
        assertEquals(1, privileges.size)

        privileges = privilegeRepository.findAll(PrivilegeQuery(parent = false).toSpecification())
        assertEquals(6, privileges.size)
    }
}
