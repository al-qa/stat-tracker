package com.gaming.stattracker.service

import com.gaming.stattracker.constants.PlayStyle
import com.gaming.stattracker.dao.CodProfile
import com.gaming.stattracker.exception.SquadException
import com.gaming.stattracker.models.JsonCodProfile
import com.gaming.stattracker.models.JsonCodProfileCreateRequest
import com.gaming.stattracker.models.JsonCodProfileUpdateRequest
import com.gaming.stattracker.repository.CodProfileRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CodProfileService(private val repository: CodProfileRepository) {

    @Transactional
    fun create(request: JsonCodProfileCreateRequest): JsonCodProfile {
        val profile = CodProfile(
            null,
            request.gamerTag,
            request.activisionId,
            request.kd,
            request.wins,
            PlayStyle.valueOf(request.playStyle.value()),
            request.userId)

        val savedProfile: CodProfile = repository.save(profile)

        return map(savedProfile)
    }

    fun get(id: Long): JsonCodProfile {
        val profile = validateProfile(id)
        return map(profile)
    }

    @Transactional
    fun update(id: Long, request: JsonCodProfileUpdateRequest): JsonCodProfile {
        validateProfile(id)

        val modifiedProfile = CodProfile(
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

    private fun validateProfile(id: Long): CodProfile {
        return repository.findByIdOrNull(id) ?: throw SquadException("Profile does not exist!")
    }

    private fun map(entity: CodProfile): JsonCodProfile {
        val profile = JsonCodProfile()
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
