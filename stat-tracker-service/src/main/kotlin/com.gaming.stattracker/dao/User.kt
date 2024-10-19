package com.gaming.stattracker.dao

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="gamer")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
    var firstName: String,
    var lastName: String,
    var displayName: String,
    var age: Long,
    var gender: String,
    var email: String,
    var password: String
)
