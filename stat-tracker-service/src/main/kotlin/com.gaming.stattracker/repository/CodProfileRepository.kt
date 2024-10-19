package com.gaming.stattracker.repository

import com.gaming.stattracker.dao.CodProfile
import org.springframework.data.repository.ListCrudRepository

interface CodProfileRepository: ListCrudRepository<CodProfile, Long>
