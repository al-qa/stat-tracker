package com.gaming.stattracker.dao

import com.gaming.stattracker.constants.PlayStyle
import jakarta.persistence.Id
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.EnumType

@Entity
class Profile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
    var gamerTag: String,
    var activisionId: String,
    var kd: Double,
    var wins: Long,
    @Enumerated(EnumType.STRING) var playStyle: PlayStyle,
    var userId: Long
)
