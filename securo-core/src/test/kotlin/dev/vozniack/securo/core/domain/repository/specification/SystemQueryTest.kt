package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.entity.System
import dev.vozniack.securo.core.domain.repository.SystemRepository
import dev.vozniack.securo.core.mock.mockSystem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SystemQueryTest @Autowired constructor(
    private val systemRepository: SystemRepository
) : AbstractUnitTest() {

    @Test
    fun `clean up`() {
        systemRepository.deleteAll()
    }

    @Test
    fun `system specification builder`() {
        val system: System = systemRepository.save(mockSystem())

        systemRepository.save(mockSystem(name = "System", code = "SYS", parent = system))
        systemRepository.save(mockSystem(name = "System Zero", code = "SYS0", parent = system))
        systemRepository.save(mockSystem(name = "Alfa Prime", code = "APRIME"))

        var systems: List<System> = systemRepository.findAll(SystemQuery().toSpecification())
        assertEquals(4, systems.size)

        systems = systemRepository.findAll(SystemQuery(name = "System").toSpecification())
        assertEquals(3, systems.size)

        systems = systemRepository.findAll(SystemQuery(code = "APR").toSpecification())
        assertEquals(1, systems.size)

        systems = systemRepository.findAll(SystemQuery(parent = true).toSpecification())
        assertEquals(2, systems.size)
    }
}
