package dev.vozniack.securo.core.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.vozniack.securo.core.AbstractWebMvcTest
import dev.vozniack.securo.core.api.dto.CreateTeamRequestDto
import dev.vozniack.securo.core.api.dto.TeamDto
import dev.vozniack.securo.core.api.dto.UpdateTeamRequestDto
import dev.vozniack.securo.core.domain.entity.Team
import dev.vozniack.securo.core.domain.repository.TeamRepository
import dev.vozniack.securo.core.mock.mockCreateTeamRequestDto
import dev.vozniack.securo.core.mock.mockTeam
import dev.vozniack.securo.core.mock.mockUpdateTeamRequestDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext

class TeamControllerTest @Autowired constructor(
    private val teamRepository: TeamRepository,
    context: WebApplicationContext
) : AbstractWebMvcTest(context) {

    @AfterEach
    fun `clean up`() {
        teamRepository.deleteAll()
    }

    @Test
    fun `find all in page`() {
        teamRepository.save(mockTeam(name = "Securo Internal", code = "SEC"))
        teamRepository.save(mockTeam(name = "Securo External", code = "SEC_EXT"))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        mockMvc.perform(
            get("/api/v1/teams/page")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `find all in list`() {
        teamRepository.save(mockTeam(name = "Securo Internal", code = "SEC"))
        teamRepository.save(mockTeam(name = "Securo External", code = "SEC_EXT"))

        // we just want to check if endpoint is fine
        // catching result is hard due to Page serialization problem in kotlin-jackson

        val teams: List<TeamDto> = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/teams/list")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(2, teams.size)
    }

    @Test
    fun `get by id`() {
        val team: Team = teamRepository.save(mockTeam())

        val teamDto: TeamDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                get("/api/v1/teams/${team.id}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(team.id, teamDto.id)
    }

    @Test
    fun `create team`() {
        val request: CreateTeamRequestDto = mockCreateTeamRequestDto()

        val teamDto: TeamDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                post("/api/v1/teams")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isCreated).andReturn().response.contentAsString
        )

        assertEquals(1, teamRepository.count())
        assertNotNull(teamDto)
    }

    @Test
    fun `update system`() {
        val team: Team = teamRepository.save(mockTeam())
        val request: UpdateTeamRequestDto = mockUpdateTeamRequestDto()

        val teamDto: TeamDto = jacksonObjectMapper().readValue(
            mockMvc.perform(
                put("/api/v1/teams/${team.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(request))
            ).andExpect(status().isOk).andReturn().response.contentAsString
        )

        assertEquals(team.id, teamDto.id)
    }

    @Test
    fun `delete team`() {
        val team: Team = teamRepository.save(mockTeam())

        mockMvc.perform(
            delete("/api/v1/teams/${team.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)

        assertEquals(0, teamRepository.count())
    }
}
