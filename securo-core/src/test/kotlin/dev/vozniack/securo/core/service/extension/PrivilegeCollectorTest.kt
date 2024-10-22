package dev.vozniack.securo.core.service.extension

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.Privilege
import dev.vozniack.securo.core.domain.entity.RolePrivilege
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.entity.User
import dev.vozniack.securo.core.domain.entity.UserPrivilege
import dev.vozniack.securo.core.domain.repository.PrivilegeRepository
import dev.vozniack.securo.core.domain.repository.RoleRepository
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.domain.repository.UserRepository
import dev.vozniack.securo.core.mock.mockPrivilege
import dev.vozniack.securo.core.mock.mockRole
import dev.vozniack.securo.core.mock.mockSystem
import dev.vozniack.securo.core.mock.mockUser
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class PrivilegeCollectorTest @Autowired constructor(
    private val systemRepository: SystemRepository,
    private val roleRepository: RoleRepository,
    private val privilegeRepository: PrivilegeRepository,
    private val userRepository: UserRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        roleRepository.findAll().forEach {
            roleRepository.save(it.apply { privileges = mutableListOf() })
        }

        userRepository.findAll().forEach {
            userRepository.save(it.apply { privileges = mutableListOf(); roles = mutableListOf() })
        }

        roleRepository.deleteAll()
        userRepository.deleteAll()
        privilegeRepository.deleteAll()
        systemRepository.deleteAll()
    }

    @Test
    fun `collect privileges`() {
        val system: System = systemRepository.save(mockSystem())

        mockPrivileges(system)
        mockRoles(system)

        val loginPrivilege: Privilege = privilegeRepository.findByCodeAndSystem("LOGIN", system)!!

        val admin: User = userRepository.save(mockUser(email = "admin@securo.com").apply {
            roles = mutableListOf(roleRepository.findByCodeAndSystem("ADM", system)!!)
            privileges = mutableListOf(UserPrivilege(user = this, privilege = loginPrivilege, excluded = false))
        })

        val supervisor: User = userRepository.save(mockUser(email = "supervisor@securo.com").apply {
            roles = mutableListOf(roleRepository.findByCodeAndSystem("SPR", system)!!)
            privileges = mutableListOf(UserPrivilege(user = this, privilege = loginPrivilege, excluded = false))
        })

        val user: User = userRepository.save(mockUser(email = "user@securo.com").apply {
            roles = mutableListOf(roleRepository.findByCodeAndSystem("USR", system)!!)
            privileges = mutableListOf(UserPrivilege(user = this, privilege = loginPrivilege, excluded = false))
        })

        // a lot of mocks, but finally the assertions

        // ADMIN

        val adminPrivileges: MutableList<Privilege> = collectPrivileges(admin)
        assertEquals(2, adminPrivileges.size)

        val adminPrivilegeCodes = adminPrivileges.collectCodes()
        assertEquals(5, adminPrivilegeCodes.size)
        assertTrue(adminPrivilegeCodes.contains("SYS_LOGIN"))
        assertTrue(adminPrivilegeCodes.contains("SYS_READ"))
        assertTrue(adminPrivilegeCodes.contains("SYS_CREATE"))
        assertTrue(adminPrivilegeCodes.contains("SYS_UPDATE"))
        assertTrue(adminPrivilegeCodes.contains("SYS_DELETE"))

        // SUPERVISOR

        val supervisorPrivileges: MutableList<Privilege> = collectPrivileges(supervisor)
        assertEquals(2, supervisorPrivileges.size)

        val supervisorPrivilegeCodes = supervisorPrivileges.collectCodes()
        assertEquals(4, supervisorPrivilegeCodes.size)
        assertTrue(adminPrivilegeCodes.contains("SYS_LOGIN"))
        assertTrue(adminPrivilegeCodes.contains("SYS_READ"))
        assertTrue(adminPrivilegeCodes.contains("SYS_CREATE"))
        assertTrue(adminPrivilegeCodes.contains("SYS_UPDATE"))

        // USER

        val userPrivileges: MutableList<Privilege> = collectPrivileges(user)
        assertEquals(3, userPrivileges.size)

        val userPrivilegeCodes = userPrivileges.collectCodes()
        assertEquals(3, userPrivilegeCodes.size)
        assertTrue(adminPrivilegeCodes.contains("SYS_LOGIN"))
        assertTrue(adminPrivilegeCodes.contains("SYS_READ"))
        assertTrue(adminPrivilegeCodes.contains("SYS_CREATE"))
    }

    private fun mockPrivileges(system: System) {
        privilegeRepository.save(mockPrivilege(name = "Login", code = "LOGIN", system = system))

        val managePrivilege: Privilege = privilegeRepository.save(
            mockPrivilege(name = "Manage", code = "MANAGE", system = system)
        )

        privilegeRepository.saveAll(
            mutableListOf(
                mockPrivilege(name = "Read", code = "READ", system = system, index = 1, parent = managePrivilege),
                mockPrivilege(name = "Create", code = "CREATE", system = system, index = 2, parent = managePrivilege),
                mockPrivilege(name = "Update", code = "UPDATE", system = system, index = 3, parent = managePrivilege),
                mockPrivilege(name = "Delete", code = "DELETE", system = system, index = 4, parent = managePrivilege)
            )
        )
    }

    private fun mockRoles(system: System) {
        val managePrivilege: Privilege = privilegeRepository.findByCodeAndSystem("MANAGE", system)!!
        val readPrivilege: Privilege = privilegeRepository.findByCodeAndSystem("READ", system)!!
        val createPrivilege: Privilege = privilegeRepository.findByCodeAndSystem("CREATE", system)!!
        val deletePrivilege: Privilege = privilegeRepository.findByCodeAndSystem("DELETE", system)!!

        roleRepository.save(mockRole(name = "Admin", code = "ADM", system = system).apply {
            privileges = mutableListOf(RolePrivilege(role = this, privilege = managePrivilege, excluded = false))
        })

        roleRepository.save(mockRole(name = "Supervisor", code = "SPR", system = system).apply {
            privileges = mutableListOf(
                RolePrivilege(role = this, privilege = managePrivilege, excluded = false),
                RolePrivilege(role = this, privilege = deletePrivilege, excluded = true)
            )
        })

        roleRepository.save(mockRole(name = "User", code = "USR", system = system).apply {
            privileges = mutableListOf(
                RolePrivilege(role = this, privilege = readPrivilege, excluded = false),
                RolePrivilege(role = this, privilege = createPrivilege, excluded = false)
            )
        })
    }
}
