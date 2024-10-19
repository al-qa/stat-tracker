package com.gaming.stattracker.model

import com.fasterxml.jackson.annotation.JsonProperty

class UserDto(
    @JsonProperty("id") var id: Long? = null,
    @JsonProperty("firstName") var firstName: String? = null,
    @JsonProperty("lastName") var lastName: String? = null,
    @JsonProperty("displayName") var displayName: String? = null,
    @JsonProperty("age") var age: Int? = null,
    @JsonProperty("gender") var gender: String? = null,
    @JsonProperty("email") var email: String? = null
)