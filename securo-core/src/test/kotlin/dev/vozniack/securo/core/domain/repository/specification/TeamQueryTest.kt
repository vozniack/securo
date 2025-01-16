package dev.vozniack.securo.core.domain.repository.specification

import dev.vozniack.securo.core.AbstractUnitTest
import dev.vozniack.securo.core.domain.ScopeType
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.mock.mockTeam
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class TeamQueryTest @Autowired constructor(
    private val teamRepository: TeamRepository
) : AbstractUnitTest() {

    @AfterEach
    fun `clean up`() {
        teamRepository.deleteAll()
    }

    @Test
    fun `team specification builder`() {
        teamRepository.save(mockTeam(name = "Securo Internal Team", code = "SEC", scope = ScopeType.INTERNAL))
        teamRepository.save(mockTeam(name = "Securo External Team", code = "SEC_EXT"))
        teamRepository.save(mockTeam(name = "Trauma Team", code = "TT"))
        teamRepository.save(mockTeam(name = "Trauma Team External", code = "TT_EXT"))

        var teams: List<Team> = teamRepository.findAll(TeamQuery().toSpecification())
        assertEquals(4, teams.size)

        teams = teamRepository.findAll(TeamQuery(name = "Securo").toSpecification())
        assertEquals(2, teams.size)

        teams = teamRepository.findAll(TeamQuery(code = "EXT").toSpecification())
        assertEquals(2, teams.size)

        teams = teamRepository.findAll(TeamQuery(scope = ScopeType.INTERNAL).toSpecification())
        assertEquals(1, teams.size)

        teams = teamRepository.findAll(TeamQuery(name = "Securo", scope = ScopeType.INTERNAL).toSpecification())
        assertEquals(1, teams.size)
    }
}
