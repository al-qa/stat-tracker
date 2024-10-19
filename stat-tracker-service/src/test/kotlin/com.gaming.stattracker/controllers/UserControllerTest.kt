package com.gaming.stattracker.controllers

import com.gaming.stattracker.exception.SquadException
import com.gaming.stattracker.models.JsonUser
import com.gaming.stattracker.service.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController::class], excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class UserControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var service: UserService

    @Test
    fun `Create User`() {
        val response = buildUserResponse()

        coEvery { service.create(any()) } returns response

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .content(buildRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.firstName").value(response.firstName))
            .andExpect(jsonPath("\$.lastName").value(response.lastName))
            .andExpect(jsonPath("\$.displayName").value(response.displayName))
            .andExpect(jsonPath("\$.age").value(response.age))
            .andExpect(jsonPath("\$.gender").value(response.gender))
            .andExpect(jsonPath("\$.email").value(response.email))
    }

    @Test
    fun `Email Linked to Existing Account`() {
        val email = "jdoe@gmail.com"
        coEvery { service.create(any()) } throws SquadException("Email %s linked to existing user!".format(email))

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .content(buildRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("\$").value("Email %s linked to existing user!".format(email)))
    }

    @Test
    fun `Username Already in Use` () {
        val username = "jdoe"
        coEvery { service.create(any()) } throws SquadException("Username %s already in use!".format(username))

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .content(buildRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("\$").value("Username %s already in use!".format(username)))
    }

    private fun buildRequest(): String {
        return """ 
            {
                "firstName": "John",
                "lastName": "Doe",
                "displayName": "jdoe",
                "age": 31,
                "gender": "male",
                "email": "jdoe@gmail.com",
                "password": "password"
            }
        """
    }

    private fun buildUserResponse(): JsonUser {
        val user = JsonUser()
        user.id = 1
        user.firstName = "John"
        user.lastName = "Doe"
        user.displayName = "jdoe"
        user.age = 31
        user.gender = "male"
        user.email = "jdoe@gmail.com"

        return user
    }

}
