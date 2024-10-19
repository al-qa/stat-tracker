package com.gaming.stattracker.controllers

import com.gaming.stattracker.exception.SquadException
import com.gaming.stattracker.models.JsonCodProfile
import com.gaming.stattracker.service.CodProfileService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [ProfileController::class], excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class ProfileControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var service: CodProfileService

    @Test
    fun `Create Profile`() {
        val response = buildProfileResponse()

        coEvery { service.create(any()) } returns response

        mockMvc.perform(post("/profiles")
            .content(buildProfileRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(response.id))
            .andExpect(jsonPath("\$.gamerTag").value(response.gamerTag))
            .andExpect(jsonPath("\$.kd").value(response.kd))
            .andExpect(jsonPath("\$.wins").value(response.wins))
    }

    @Test
    fun `Get Profile`() {
        val response = buildProfileResponse()

        coEvery { service.get(1) } returns response

        mockMvc.perform(get("/profiles/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(response.id))
            .andExpect(jsonPath("\$.gamerTag").value(response.gamerTag))
            .andExpect(jsonPath("\$.kd").value(response.kd))
            .andExpect(jsonPath("\$.wins").value(response.wins))
    }

    @Test
    fun `Unable to get Profile`() {
        coEvery { service.get(1) } throws SquadException("Profile does not exist!")

        mockMvc.perform(get("/profiles/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("\$").value("Profile does not exist!"))
    }

    @Test
    fun `Update Profile`() {
        val response = buildProfileResponse()

        coEvery { service.update(1, any()) } returns response

        mockMvc.perform(put("/profiles/1")
            .content(buildProfileRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(response.id))
            .andExpect(jsonPath("\$.gamerTag").value(response.gamerTag))
            .andExpect(jsonPath("\$.kd").value(response.kd))
            .andExpect(jsonPath("\$.wins").value(response.wins))
    }

    @Test
    fun `Profile Does Not Exist`() {
        coEvery { service.update(1, any()) } throws SquadException("Profile does not exist!")

        mockMvc.perform(put("/profiles/1")
            .content(buildProfileRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("\$").value("Profile does not exist!"))
    }

    private fun buildProfileRequest(): String {
        return """ 
            {
                "gamerTag": "test_gamer",
                "activisionId": "test_gamer#12345",
                "kd": 1.05,
                "wins": 105,
                "playStyle": "CAUTIOUS",
                "userId": 1
            }
        """
    }

    private fun buildProfileResponse(): JsonCodProfile {
        val profile = JsonCodProfile()
        profile.id = 1
        profile.gamerTag = "test_gamer"
        profile.activisionId = "test_gamer#12345"
        profile.kd = 1.05
        profile.wins = 105
        profile.playStyle = "CAUTIOUS"
        profile.userId = 1

        return profile
    }

}
