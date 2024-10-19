package com.gaming.stattracker.repository

import com.gaming.stattracker.dao.Profile
import org.springframework.data.repository.ListCrudRepository

interface ProfileRepository: ListCrudRepository<Profile, Long>
