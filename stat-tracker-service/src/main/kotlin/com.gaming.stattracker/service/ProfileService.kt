package com.gaming.stattracker.service

import com.gaming.stattracker.constants.PlayStyle
import com.gaming.stattracker.dao.Profile
import com.gaming.stattracker.exception.SquadException
import com.gaming.stattracker.models.JsonProfile
import com.gaming.stattracker.models.JsonProfileCreateRequest
import com.gaming.stattracker.models.JsonProfileUpdateRequest
import com.gaming.stattracker.repository.ProfileRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(private val repository: ProfileRepository) {

    @Transactional
    fun create(request: JsonProfileCreateRequest): JsonProfile {
        val profile = Profile(
            null,
            request.gamerTag,
            request.activisionId,
            request.kd,
            request.wins,
            PlayStyle.valueOf(request.playStyle.value()),
            request.userId)

        val savedProfile: Profile = repository.save(profile)

        return map(savedProfile)
    }

    fun get(id: Long): JsonProfile {
        val profile = validateProfile(id)
        return map(profile)
    }

    @Transactional
    fun update(id: Long, request: JsonProfileUpdateRequest): JsonProfile {
        validateProfile(id)

        val modifiedProfile = Profile(
            id,
            request.gamerTag,
            request.activisionId,
            request.kd,
            request.wins,
            PlayStyle.valueOf(request.playStyle.value()),
            request.userId)

        val updatedProfile = repository.save(modifiedProfile)

        return map(updatedProfile)
    }

    private fun validateProfile(id: Long): Profile {
        return repository.findByIdOrNull(id) ?: throw SquadException("Profile does not exist!")
    }

    private fun map(entity: Profile): JsonProfile {
        val profile = JsonProfile()
        profile.id = entity.id
        profile.gamerTag = entity.gamerTag
        profile.activisionId = entity.activisionId
        profile.kd = entity.kd
        profile.wins = entity.wins
        profile.playStyle = entity.playStyle.name
        profile.userId = entity.userId

        return profile
    }

}
