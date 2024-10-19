package com.gaming.stattracker.service

import com.gaming.stattracker.dao.User
import com.gaming.stattracker.exception.SquadException
import com.gaming.stattracker.models.JsonUser
import com.gaming.stattracker.models.JsonUserCreateRequest
import com.gaming.stattracker.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(private val repository: UserRepository, val passwordEncoder: PasswordEncoder) {

    fun create(request: JsonUserCreateRequest): JsonUser {
        val email = request.email
        val displayName = request.displayName

        validateEmail(email)
        validateDisplayName(displayName)

        val newUser = User(
            null,
            request.firstName,
            request.lastName,
            displayName,
            request.age,
            request.gender,
            email,
            passwordEncoder.encode(request.password)
        )

        val savedUser = repository.save(newUser)

        return map(savedUser)
    }

    private fun validateEmail(email: String) {
        val existingEmail = repository.findUserByEmail(email)
        if (existingEmail != null) {
            throw SquadException("Email %s linked to existing user!".format(email))
        }
    }

    private fun validateDisplayName(displayName: String) {
        val existingUsername = repository.findUserByDisplayName(displayName)
        if (existingUsername != null) {
            throw SquadException("Username %s already in use!".format(displayName))
        }
    }

    private fun map(entity: User) : JsonUser {
        val user = JsonUser()
        user.id = entity.id
        user.firstName = entity.firstName
        user.lastName = entity.lastName
        user.displayName = entity.displayName
        user.age = entity.age
        user.gender = entity.gender
        user.email = entity.email

        return user
    }

}
