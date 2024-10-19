package com.gaming.stattracker.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.gaming.stattracker.constants.PlayStyle

class CodProfileDto(
    @JsonProperty("id") var id: Long? = null,
    @JsonProperty("gamerTag") var gamerTag: String? = null,
    @JsonProperty("activisionId") var activisionId: String? = null,
    @JsonProperty("kd") var kd: Double? = null,
    @JsonProperty("wins") var wins: Int? = null,
    @JsonProperty("playStyle") var playStyle: PlayStyle? = null,
    @JsonProperty("userId") var userId: Long? = null
)
