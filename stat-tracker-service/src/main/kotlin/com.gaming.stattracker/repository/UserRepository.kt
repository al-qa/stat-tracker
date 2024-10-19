package com.gaming.stattracker.repository

import com.gaming.stattracker.dao.User
import org.springframework.data.repository.ListCrudRepository

interface UserRepository: ListCrudRepository<User, Long> {

    fun findUserByEmail(email: String?) : User?

    fun findUserByDisplayName(displayName: String) : User?

}
